package net.jakot.demo.keycloakimp.user

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Suppress("JpaObjectClassSignatureInspection")
@Entity
class UserEntity(
    @Id
    val id: String,
    val username: String,
    val email: String,
    val passwordPlain: String,
    val passwordHash: String,
    val firstName: String,
    val lastName: String
)