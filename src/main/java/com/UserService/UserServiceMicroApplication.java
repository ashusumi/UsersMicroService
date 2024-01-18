package com.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import com.UserService.feignClient.service.RestTempInterceptor;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class UserServiceMicroApplication {

	
	@Autowired
	ClientRegistrationRepository repo1;;
	
	@Autowired
	OAuth2AuthorizedClientRepository repo2;
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceMicroApplication.class, args);
	}

	@Bean
	@LoadBalanced
	 RestTemplate restTemplate() {
		
		RestTemplate restTemplate=new RestTemplate();
		List<ClientHttpRequestInterceptor> request=new ArrayList<ClientHttpRequestInterceptor>();
		request.add(new RestTempInterceptor(auth2AuthorizedClientManager(repo1, repo2)));
		
		
		return restTemplate;
	}

	@Bean
	OAuth2AuthorizedClientManager auth2AuthorizedClientManager(ClientRegistrationRepository repository,OAuth2AuthorizedClientRepository repository2) {
		OAuth2AuthorizedClientProvider provider=OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
		DefaultOAuth2AuthorizedClientManager manager=new DefaultOAuth2AuthorizedClientManager(repository, repository2);
		manager.setAuthorizedClientProvider(provider);
		return manager;
		
	}
	
}
