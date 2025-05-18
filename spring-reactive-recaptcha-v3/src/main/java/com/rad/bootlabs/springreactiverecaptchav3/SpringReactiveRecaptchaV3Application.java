package com.rad.bootlabs.springreactiverecaptchav3;

import com.rad.bootlabs.springreactiverecaptchav3.configuration.CaptchaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(CaptchaProperties.class)
public class SpringReactiveRecaptchaV3Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveRecaptchaV3Application.class, args);
	}


	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
