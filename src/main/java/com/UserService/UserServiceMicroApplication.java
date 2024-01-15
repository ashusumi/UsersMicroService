package com.UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class UserServiceMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceMicroApplication.class, args);
	}

	@Bean
	@LoadBalanced
	 RestTemplate restTemplate() {
		return new RestTemplate();
	}
}