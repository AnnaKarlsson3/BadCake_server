package com.dkserver.danielServer;

import com.dkserver.danielServer.interceptor.TenantSchemaResolver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DanielServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanielServerApplication.class, args);
	}


}
