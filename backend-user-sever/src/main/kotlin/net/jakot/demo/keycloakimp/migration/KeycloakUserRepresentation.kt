package net.jakot.demo.keycloakimp.migration

data class KeycloakUserRepresentation(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val emailVerified: Boolean,
    val enabled: Boolean,
    val realmRoles: List<String>,
    val credentials: List<CredentialRepresentation>
)

data class CredentialRepresentation(
    val algorithm: String,
    val hashedSaltedValue: String,
    val hashIterations: Int,
    val type: String,
    val salt: String
)