/**
 * Copyright 2014 Meruvian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.webapi.config.oauth;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class OAuthServerConfig {
	@Inject
	private ClientDetailsService clientDetailsService;
		
	@Bean
	public AccessTokenConverter tokenConverter() {
		DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
		converter.setUserTokenConverter(new UserTokenConverter());
		
		return converter;
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		JwtAccessTokenConverter enhancer = new JwtAccessTokenConverter();
		enhancer.setAccessTokenConverter(tokenConverter());
		
		return enhancer;
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore((JwtAccessTokenConverter) tokenEnhancer());
	}
	
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenEnhancer(tokenEnhancer());
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(clientDetailsService);
		
		return tokenServices;
	}

	@Bean
	public OAuth2RequestFactory requestFactory() {
		return new DefaultOAuth2RequestFactory(clientDetailsService);
	}
	
	@Bean
	public UserApprovalHandler userApprovalHandler(ApprovalStore approvalStore) {
		ApprovalStoreUserApprovalHandler approvalHandler = new ApprovalStoreUserApprovalHandler();
		approvalHandler.setApprovalStore(approvalStore);
		approvalHandler.setClientDetailsService(clientDetailsService);
		approvalHandler.setRequestFactory(requestFactory());
		
		return approvalHandler;
	}
	
	@Bean
	public TokenGranter tokenGranter() {
		return new ClientCredentialsTokenGranter(tokenServices(), clientDetailsService, requestFactory());
	}
	
	@Bean
	public UserDetailsService clientDetailsUserDetailsService() {
		return new ClientDetailsUserDetailsService(clientDetailsService);
	}
	
	@Bean
	public AuthenticationEntryPoint oauth2AuthenticationEntryPoint() {
		OAuth2AuthenticationEntryPoint e =  new OAuth2AuthenticationEntryPoint();
		e.setRealmName("yama/client");
		e.setTypeName("Basic");
		
		return e;
	}
	
	@Bean
	public AccessDeniedHandler oauth2AccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}
}
