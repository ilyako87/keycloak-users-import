package net.jakot.demo.keycloakimp.keycloak

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfiguration {
    @Value("\${keycloak.issuer.uri}")
    private lateinit var issuerUri: String
    @Value("\${keycloak.client.id}")
    private lateinit var clientId: String
    @Value("\${keycloak.client.secret}")
    private lateinit var clientSecret: String
    @Value("\${keycloak.admin.uri}")
    private lateinit var keycloakAdminUri: String
    @Value("\${keycloak.realm.name}")
    private lateinit var customerRealmName: String

    @Bean
    fun keycloakConfig() = KeycloakConfig(issuerUri, clientId, clientSecret, keycloakAdminUri, customerRealmName)

}

data class KeycloakConfig(
    val issuerUri: String,
    val clientId: String,
    val clientSecret: String,
    val keycloakAdminUri: String,
    val customerRealmName: String
)