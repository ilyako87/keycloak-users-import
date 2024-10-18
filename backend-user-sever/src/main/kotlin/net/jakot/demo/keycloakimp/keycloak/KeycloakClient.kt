package net.jakot.demo.keycloakimp.keycloak

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.PostConstruct
import net.jakot.demo.keycloakimp.migration.KeycloakUserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class KeycloakClient(private val keycloakConfig: KeycloakConfig) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val restTemplate = RestTemplate()

    @PostConstruct
    fun init() {
        restTemplate.interceptors.add(KeycloakAuthInterceptor(keycloakConfig))
        logger.debug("KeycloakClient initialized.")
    }

    fun createUser(user: KeycloakUserRepresentation) {
        restTemplate.exchange(getKeycloakUsersEndpoint(), HttpMethod.POST, HttpEntity(user), Unit::class.java)
    }

    private fun getKeycloakUsersEndpoint(): String {
        return keycloakConfig.keycloakAdminUri + "/users"
    }
}

private class KeycloakAuthInterceptor(private val keycloakConfig: KeycloakConfig) : ClientHttpRequestInterceptor {

    private val restTemplate = RestTemplate()
    private var accessToken: String? = null

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        accessToken?.let { token ->
            request.headers.setBearerAuth(token)
            val response = execution.execute(request, body)
            if (HttpStatus.UNAUTHORIZED == response.statusCode) {
                accessToken = getNewAccessToken()
                request.headers.remove("Authorization")
                request.headers.setBearerAuth(accessToken!!)
                return execution.execute(request, body)
            }
            return response
        } ?: run {
            accessToken = getNewAccessToken()
            request.headers.setBearerAuth(accessToken!!)
            return execution.execute(request, body)
        }
        return execution.execute(request, body)
    }

    private fun getNewAccessToken(): String {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val tokenRequestBody = "grant_type=client_credentials&client_id=${keycloakConfig.clientId}&client_secret=${keycloakConfig.clientSecret}"
        val request = HttpEntity(tokenRequestBody, headers)
        val response = restTemplate.postForObject(
            "${keycloakConfig.issuerUri}/protocol/openid-connect/token",
            request,
            TokenResponse::class.java
        )

        return response?.accessToken ?: throw RuntimeException("Failed to get access token for Keycloak Admin API")
    }

    private data class TokenResponse(
        @JsonProperty("access_token")
        val accessToken: String,
        @JsonProperty("token_type")
        val tokenType: String,
        @JsonProperty("expires_in")
        val expiresIn: Int
    )
}