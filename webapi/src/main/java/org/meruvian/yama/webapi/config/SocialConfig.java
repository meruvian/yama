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

import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.connection.SocialConnectionRepository;
import org.meruvian.yama.social.core.SocialConnectionService;
import org.meruvian.yama.social.core.SocialServiceLocator;
import org.meruvian.yama.social.core.SocialServiceRegistry;
import org.meruvian.yama.social.core.SocialUsersConnectionService;
import org.meruvian.yama.social.facebook.FacebookService;
import org.meruvian.yama.social.google.GooglePlusService;
import org.meruvian.yama.web.SessionCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class SocialConfig {
	@Inject
	private Environment env;
	
	@Bean
	public SocialServiceLocator socialServiceLocator() {
		SocialServiceRegistry registry = new SocialServiceRegistry();
		registry.addSocialService(facebookService());
		registry.addSocialService(googlePlusService());
		
		return registry;
	}
	
	@Bean
	public SocialUsersConnectionService usersConnectionRepository(SocialServiceLocator locator,
			SocialConnectionRepository repository, ConnectionSignUp connectionSignUp) {
		SocialUsersConnectionService s = new SocialUsersConnectionService(locator, repository);
		s.setConnectionSignUp(connectionSignUp);
		
		return s;
	}
	
	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public SocialConnectionService connectionRepository(SocialUsersConnectionService usersConnectionRepository) {
		User user = SessionCredentials.getCurrentUser();
		
		if (user == null) return null;
		
		return usersConnectionRepository.createConnectionRepository(user.getId());
	}
	
	@Bean
	public FacebookService facebookService() {
		String appId = env.getProperty("social.facebook.appId");
		String appSecret = env.getProperty("social.facebook.appSecret");
		String redirectUri = env.getProperty("social.facebook.redirectUri");
		String scope = env.getProperty("social.facebook.scope");
		
		FacebookConnectionFactory factory = new FacebookConnectionFactory(appId, appSecret);
		FacebookService facebookService = new FacebookService(factory);
		facebookService.setRedirectUri(redirectUri);
		facebookService.setScope(scope);
		
		return facebookService;
	}
	
	@Bean
	public GooglePlusService googlePlusService() {
		String appId = env.getProperty("social.google.appId");
		String appSecret = env.getProperty("social.google.appSecret");
		String redirectUri = env.getProperty("social.google.redirectUri");
		String scope = env.getProperty("social.google.scope");
		
		GoogleConnectionFactory factory = new GoogleConnectionFactory(appId, appSecret);
		GooglePlusService gPlusService = new GooglePlusService(factory);
		gPlusService.setRedirectUri(redirectUri);
		gPlusService.setScope(scope);
		
		return gPlusService;
	}
}
