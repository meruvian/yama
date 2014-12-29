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
package org.meruvian.yama.webapi.config;

import javax.inject.Inject;

import org.meruvian.yama.web.security.oauth.DefaultClientDetailsService;
import org.meruvian.yama.web.security.oauth.OauthApplicationApprovalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class Oauth2ServerConfig {
	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
		@Inject
		private DefaultClientDetailsService clientDetailsService;
		
		@Inject
		private OauthApplicationApprovalService approvalService;
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(clientDetailsService);
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.accessTokenConverter(tokenConverter())
					.approvalStore(approvalService)
					.authenticationManager(authenticationManager())
					.clientDetailsService(clientDetailsService)
					.tokenEnhancer(tokenConverter())
					.tokenGranter(tokenGranter())
					.tokenServices(tokenServices());
		}
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		}
		
		@Bean
		public JwtAccessTokenConverter tokenConverter() {
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
			converter.setSigningKey("yama");
			converter.setAccessTokenConverter(new JwtAccessTokenConverter());
			
			return converter;
		}
		
		@Bean
		public TokenStore tokenStore() {
			return new JwtTokenStore(tokenConverter());
		}
		
		@Bean
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setTokenStore(tokenStore());
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setClientDetailsService(clientDetailsService);
			tokenServices.setTokenEnhancer(tokenConverter());
			
			return tokenServices;
		}
		
		@Bean
		public AuthenticationManager authenticationManager() {
			OAuth2AuthenticationManager authManager = new OAuth2AuthenticationManager();
			authManager.setTokenServices(tokenServices());
			
			return authManager;
		}
		
		@Bean
		public OAuth2RequestFactory requestFactory() {
			return new DefaultOAuth2RequestFactory(clientDetailsService);
		}
		
		@Bean
		public TokenGranter tokenGranter() {
			return new ClientCredentialsTokenGranter(tokenServices(), clientDetailsService, 
					requestFactory());
		}
	}
}
