package com.cdcm.backend;

import com.cdcm.backend.entity.UserEntity;
import com.cdcm.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class ModuleVoiceBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ModuleVoiceBackendApplication.class, args);
	}
}
