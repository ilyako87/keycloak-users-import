package net.jakot.demo.keycloakimp.migration

import org.springframework.context.ApplicationEvent

class UserMigrationEvent(source: Any): ApplicationEvent(source)