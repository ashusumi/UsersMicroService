package com.UserService.feignClient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
@Configuration
@Component
public class FeingClientInterceptor implements RequestInterceptor {

    @Autowired
    private OAuth2AuthorizedClientManager manager;

    @Override
    public void apply(RequestTemplate template) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client").principal("internal").build();
        OAuth2AuthorizedClient authorizedClient = manager.authorize(authorizeRequest);

        if (authorizedClient != null && authorizedClient.getAccessToken() != null) {
            System.out.println(authorizedClient.getAccessToken().getTokenValue());
            
            template.header("Authorization", "Bearer " + authorizedClient.getAccessToken().getTokenValue());
        } else {
            System.out.println("Failed to obtain a valid access token.");
        }
    }
}
