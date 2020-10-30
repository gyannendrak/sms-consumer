package com.ghf.sms;

import com.ghf.sms.service.SMSTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.debug("Started SMS Application");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Async
	public SMSTemplate smsTemplate() {
		return new SMSTemplate();
	}
}
