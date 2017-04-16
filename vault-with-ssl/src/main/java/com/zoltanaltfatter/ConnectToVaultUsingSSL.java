package com.zoltanaltfatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.SslConfiguration;
import org.springframework.vault.support.VaultResponseSupport;

import java.io.File;

/**
 * @author Zoltan Altfatter
 */
@SpringBootApplication
@Slf4j
public class ConnectToVaultUsingSSL implements CommandLineRunner {

    @Autowired
    private VaultTemplate vaultTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ConnectToVaultUsingSSL.class, args);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void run(String... args) throws Exception {

        Secret secret = new Secret("bar");

        vaultTemplate.write("secret/myApp/foo", secret);
        log.info("Wrote data to Vault");

        VaultResponseSupport<Secret> response = vaultTemplate.read("secret/myApp/foo", Secret.class);
        log.info("Retrieved data {} from Vault", response.getData().getPassword());

    }

    @Getter
    @AllArgsConstructor
    static class Secret {
        String password;
    }

    @Configuration
    static class VaultConfiguration extends AbstractVaultConfiguration {

        @Override
        public VaultEndpoint vaultEndpoint() {
            return new VaultEndpoint();
        } // defaults to https://localhost:8200

        @Override
        public ClientAuthentication clientAuthentication() {
            return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
        }

        @Override
        public SslConfiguration sslConfiguration() {
            return SslConfiguration.forTrustStore(new FileSystemResource(
                    new File("/Users/zoltan/projects/personal/vault-demo/work", "keystore.jks")), "changeit");
        }

    }
}
