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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.meruvian.yama.core.user.User;
import org.meruvian.yama.web.security.DefaultUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
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
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.StringUtils;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class Oauth2ServerConfig {

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
		@Inject
		private DefaultTokenServices tokenServices;
		
		@Inject
		private ClientDetailsService clientDetailsService;
		
		@Inject
		@Named("tokenConverter")
		private AccessTokenConverter accessTokenConverter;
		
		@Inject
		@Named("authenticationManagerBean")
		private AuthenticationManager authenticationManager;
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.withClientDetails(clientDetailsService);
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager)
				.tokenServices(tokenServices)
				.accessTokenConverter(accessTokenConverter);
		}
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
		@Inject
		private DefaultTokenServices tokenServices;
		
		@Inject
		@Named("clientDetailsUserDetailsService")
		private UserDetailsService clientDetailsUserDetailsService;
		
		@Inject
		@Named("oauth2AuthenticationEntryPoint")
		private AuthenticationEntryPoint authenticationEntryPoint;
		
		@Inject
		@Named("oauth2AccessDeniedHandler")
		private AccessDeniedHandler accessDeniedHandler;
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http
				.requestMatchers()
					.antMatchers("/oauth/token", "/roles", "/roles/**", 
							"/users", "/users/**")
					.and()
				.authorizeRequests()
					.antMatchers("/oauth/token").fullyAuthenticated()
					.antMatchers("/**").fullyAuthenticated()
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.userDetailsService(clientDetailsUserDetailsService)
				.anonymous().disable()
				.headers()
				.frameOptions().disable()
				.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler);
		}
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.tokenServices(tokenServices);
		}
	}
	
	@Configuration
	protected static class Oauth2Stuff {
		@Inject
		private ClientDetailsService clientDetailsService;
		
		@Inject
		private ApprovalStore approvalStore;
		
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
	
	static class UserTokenConverter extends DefaultUserAuthenticationConverter {
		protected static final String USER_ID = "user_id";
		
		public Map<String, ?> convertUserAuthentication(Authentication authentication) {
			Map<String, Object> response = new LinkedHashMap<String, Object>();
			response.put(USERNAME, authentication.getName());
			
			if (authentication.getPrincipal() instanceof DefaultUserDetails) {
				DefaultUserDetails details = (DefaultUserDetails) authentication.getPrincipal();
				response.put(USER_ID, details.getId());
			}
			
			if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
				response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
			}
			
			return response;
		}

		public Authentication extractAuthentication(Map<String, ?> map) {
			if (map.containsKey(USERNAME) && map.containsKey(USER_ID)) {
				DefaultUserDetails details = new DefaultUserDetails((String) map.get(USERNAME),
						"N/A", getAuthorities(map));
				details.setId((String) map.get(USER_ID));
				
				User user = new User();
				user.setId(details.getId());
				user.setUsername(details.getUsername());
				details.setUser(user);
				
				return new UsernamePasswordAuthenticationToken(details, details.getPassword(),
						details.getAuthorities());
			}
			
			return null;
		}
		
		private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
			Object authorities = map.get(AUTHORITIES);
			
			if (authorities instanceof String) {
				AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
			}
			
			if (authorities instanceof Collection) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
						.collectionToCommaDelimitedString((Collection<?>) authorities));
			}
			
			throw new IllegalArgumentException("Authorities must be either a String or a Collection");
		}
	}
}
