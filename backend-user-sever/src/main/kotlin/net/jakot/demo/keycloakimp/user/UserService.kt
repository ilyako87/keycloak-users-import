package net.jakot.demo.keycloakimp.user

import com.github.javafaker.Faker
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.util.UUID

private const val USER_COUNT = 10
private const val USER_COUNT_THRESHOLD = 1

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val faker = Faker()

    @PostConstruct
    fun init() {
        createUsersIfNeeded()
    }


    @EventListener(UserGenerationEvent::class)
    fun onUserGenerationEvent(event: UserGenerationEvent) {
        logger.info("UserGenerationEvent received: $event")
        createUsers()
    }


    fun findAllUsers(request: ServerRequest): ServerResponse {
        logger.debug("request received: {}", request.path())
        return ServerResponse.ok()
            .body(userRepository.findAll())
    }

    fun createUsersIfNeeded() {
        if (userRepository.count() < USER_COUNT_THRESHOLD) {
            createUsers()
        }
    }

    private fun createUsers() {
        repeat(USER_COUNT) {
            userRepository.save(generateUser())
        }
    }

    private fun generateUser(): UserEntity {
        val password = faker.internet().password(true)
        val firstName = faker.name().firstName()
        val lastName = faker.name().lastName()
        val username = firstName.lowercase() + "." + lastName.lowercase()
        return UserEntity(
            id = UUID.randomUUID().toString(),
            username = username,
            email = faker.internet().emailAddress(username),
            passwordPlain = password,
            passwordHash = passwordEncoder.encode(password),
            firstName = firstName,
            lastName = lastName
        )
    }

}