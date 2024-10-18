package net.jakot.demo.keycloakimp.keycloak

import net.jakot.demo.keycloakimp.migration.KeycloakUserRepresentation

interface KeycloakApi {
    fun createUser(user: KeycloakUserRepresentation): KeycloakUserRepresentation
    fun createUsers(users: List<KeycloakUserRepresentation>): List<KeycloakUserRepresentation>
}