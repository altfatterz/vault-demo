package com.zoltanaltfatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.vault.annotation.VaultPropertySource;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

import java.net.URI;

/**
 * @author Zoltan Altfatter
 */
@Slf4j
@SpringBootApplication
public class VaultPropertySourceExample implements CommandLineRunner {

    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(VaultPropertySourceExample.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Retrieving data through Spring Environment using @ValuePropertySource from path secret/myApp/config");

        log.info("database.url:{}", env.getProperty("database.url"));
        log.info("database.username:{}", env.getProperty("database.username"));
        log.info("database.password:{}", env.getProperty("database.password"));
    }

    @Configuration
    @VaultPropertySource("secret/myApp/config")
    static class VaultPropertySourceConfiguration extends AbstractVaultConfiguration {

        @Override
        public VaultEndpoint vaultEndpoint() {
            return VaultEndpoint.from(URI.create("http://localhost:8200"));
        }

        @Override
        public ClientAuthentication clientAuthentication() {
            return new TokenAuthentication("00000000-0000-0000-0000-000000000000");
        }
    }
}