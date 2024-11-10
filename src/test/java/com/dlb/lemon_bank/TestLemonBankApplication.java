package com.dlb.lemon_bank;

import org.springframework.boot.SpringApplication;

public class TestLemonBankApplication {

	public static void main(String[] args) {
		SpringApplication.from(LemonBankApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
