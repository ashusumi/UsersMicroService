package com.UserService.feignClient.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

public class RestTempInterceptor implements ClientHttpRequestInterceptor{

	@Autowired
	private OAuth2AuthorizedClientManager manager;
	
	
	
	public RestTempInterceptor(OAuth2AuthorizedClientManager manager) {
		super();
		this.manager = manager;
	}



	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		OAuth2AuthorizedClient token =manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build());
		System.out.println(token.getAccessToken().getTokenValue());
		request.getHeaders().add("Authorization","Bearer " +token.getAccessToken().getTokenValue());
		
		return execution.execute(request, body);
	}

}
