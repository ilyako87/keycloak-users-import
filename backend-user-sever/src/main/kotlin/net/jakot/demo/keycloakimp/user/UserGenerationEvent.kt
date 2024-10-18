package net.jakot.demo.keycloakimp.user

import org.springframework.context.ApplicationEvent

class UserGenerationEvent(source: Unit): ApplicationEvent(source)