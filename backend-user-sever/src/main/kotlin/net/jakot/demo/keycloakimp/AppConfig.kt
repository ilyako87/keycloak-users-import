package net.jakot.demo.keycloakimp

import net.jakot.demo.keycloakimp.migration.UserMigrationEvent
import net.jakot.demo.keycloakimp.user.UserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Configuration
class AppConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun mainRouter(userService: UserService, eventPublisher: ApplicationEventPublisher) = router {
        "/".nest {
            accept(APPLICATION_JSON).nest {
                GET("/users", userService::findAllUsers)
            }
            accept(APPLICATION_JSON).nest {
                GET("/migrate") {
                    eventPublisher.publishEvent(UserMigrationEvent(""))
                    ServerResponse.accepted().build()
                }
            }
        }
    }
}