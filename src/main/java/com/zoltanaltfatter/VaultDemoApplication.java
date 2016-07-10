package com.zoltanaltfatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class VaultDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaultDemoApplication.class, args);
	}

	@Value("${mykey}")
	String mykey;

	@PostConstruct
	private void postConstruct() {
		log.info("mykey:" + mykey);
	}

}
