package net.jakot.demo.keycloakimp.hash;

import org.apache.commons.lang3.ArrayUtils;
import org.keycloak.credential.hash.PasswordHashProvider;
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class BCryptHashProvider implements PasswordHashProvider {

    private final String providerID;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public BCryptHashProvider(String providerID) {
        this.providerID = providerID;
    }

    @Override
    public boolean policyCheck(PasswordPolicy passwordPolicy, PasswordCredentialModel passwordCredentialModel) {
        return providerID.equals(passwordCredentialModel.getPasswordCredentialData().getAlgorithm());
    }

    @Override
    public PasswordCredentialModel encodedCredential(String rawPassword, int iterations) {
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        return PasswordCredentialModel.createFromValues(providerID, ArrayUtils.EMPTY_BYTE_ARRAY, iterations, encodedPassword);
    }

    @Override
    public boolean verify(String rawPassword, PasswordCredentialModel passwordCredentialModel) {
        return bCryptPasswordEncoder.matches(rawPassword, passwordCredentialModel.getPasswordSecretData().getValue());
    }

    @Override
    public void close() {
        //not implemented
    }
}
