package net.jakot.demo.keycloakimp.hash;

import org.keycloak.Config;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.credential.hash.PasswordHashProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class BCryptPasswordHashProviderFactory implements PasswordHashProviderFactory {

    private static final String ID = "bcrypt";

    @Override
    public PasswordHashProvider create(KeycloakSession keycloakSession) {
        return new BCryptHashProvider(ID);
    }

    @Override
    public void init(Config.Scope scope) {
        // not implemented
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // not implemented
    }

    @Override
    public void close() {
        // not implemented
    }

    @Override
    public String getId() {
        return ID;
    }
}
