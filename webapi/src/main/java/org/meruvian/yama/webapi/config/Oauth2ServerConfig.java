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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Dian Aditya
 *
 */
@Configuration
@ImportResource({ "classpath:config/oauth2/authorization-server.xml",
		"classpath:config/oauth2/resource-server.xml" })
public class Oauth2ServerConfig {
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
	public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	    @Override
	    protected MethodSecurityExpressionHandler createExpressionHandler() {
	        return new OAuth2MethodSecurityExpressionHandler();
	    }
	}
	
	@Configuration
	public static class Oauth2Stuff {
		@Inject
		private ClientDetailsService clientDetailsService;
		
		@Inject
		private ApprovalStore approvalStore;
		
		@Bean
		public AccessTokenConverter tokenConverter() {
			return new JwtAccessTokenConverter();
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
		public UserApprovalHandler userApprovalHandler() {
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
	}
}
