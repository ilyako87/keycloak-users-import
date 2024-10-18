package net.jakot.demo.keycloakimp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeycloakUsersImportApplication

fun main(args: Array<String>) {
    runApplication<KeycloakUsersImportApplication>(*args)
}
