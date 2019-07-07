package com.scastellanos.mastermind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.scastellanos.mastermind")
public class MastermindApplication {

	public static void main(String[] args) {
		SpringApplication.run(MastermindApplication.class, args);
	}

}
