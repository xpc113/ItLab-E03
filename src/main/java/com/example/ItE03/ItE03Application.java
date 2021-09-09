package com.example.ItE03;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ItE03Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ItE03Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.trace("args: {}", (Object[]) args);
	}
}
