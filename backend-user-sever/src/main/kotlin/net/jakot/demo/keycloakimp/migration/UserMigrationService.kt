package net.jakot.demo.keycloakimp.migration

import net.jakot.demo.keycloakimp.keycloak.KeycloakClient
import net.jakot.demo.keycloakimp.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class UserMigrationService(
    private val userRepository: UserRepository,
    private val keycloakClient: KeycloakClient
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener(UserMigrationEvent::class)
    fun onUserMigrationEvent(event: UserMigrationEvent) {
        logger.info("UserMigrationEvent received: $event")
        userRepository.findAll().forEach { userEntity ->
            val keycloakUser = KeycloakUserRepresentation(
                id = userEntity.id,
                username = userEntity.username,
                email = userEntity.email,
                firstName = userEntity.firstName,
                lastName = userEntity.lastName,
                emailVerified = true,
                enabled = true,
                realmRoles = listOf("simple-user"),
                credentials = listOf(CredentialRepresentation(
                    algorithm = "bcrypt",
                    hashedSaltedValue = userEntity.passwordHash,
                    hashIterations =1000 ,
                    type = "password",
                    salt = "salt",
                ))
            )
            keycloakClient.createUser(keycloakUser)
            logger.info("Migrated user: $keycloakUser")
        }
    }

}