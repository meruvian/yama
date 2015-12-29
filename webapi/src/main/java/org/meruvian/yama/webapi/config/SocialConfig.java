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

import org.apache.commons.lang3.RandomStringUtils;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.connection.SocialConnectionRepository;
import org.meruvian.yama.social.core.SocialConnectionService;
import org.meruvian.yama.social.core.SocialServiceLocator;
import org.meruvian.yama.social.core.SocialServiceRegistry;
import org.meruvian.yama.social.core.SocialUsersConnectionService;
import org.meruvian.yama.social.facebook.FacebookService;
import org.meruvian.yama.social.github.GithubService;
import org.meruvian.yama.social.google.GooglePlusService;
import org.meruvian.yama.social.linkedin.LinkedInService;
import org.meruvian.yama.social.mervid.MervidService;
import org.meruvian.yama.social.mervid.connect.MervidConnectionFactory;
import org.meruvian.yama.web.SessionCredentials;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.linkedin.connect.LinkedInConnectionFactory;

/**
 * @author Dian Aditya
 *
 */
@Configuration
public class SocialConfig implements EnvironmentAware {
	private RelaxedPropertyResolver env;
	
	@Bean
	public SocialServiceLocator socialServiceLocator() {
		SocialServiceRegistry registry = new SocialServiceRegistry();
		registry.addSocialService(facebookService());
		registry.addSocialService(googlePlusService());
		registry.addSocialService(mervidService());
		registry.addSocialService(linkedInService());
		registry.addSocialService(githubService());
		
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
		String appId = env.getProperty("facebook.appId");
		String appSecret = env.getProperty("facebook.appSecret");
		String redirectUri = env.getProperty("facebook.redirectUri");
		String scope = env.getProperty("facebook.scope");
		
		FacebookConnectionFactory factory = new FacebookConnectionFactory(appId, appSecret);
		FacebookService facebookService = new FacebookService(factory);
		facebookService.setRedirectUri(redirectUri);
		facebookService.setScope(scope);
		facebookService.setState(RandomStringUtils.randomAlphanumeric(20));
		
		return facebookService;
	}
	
	@Bean
	public GooglePlusService googlePlusService() {
		String appId = env.getProperty("google.appId");
		String appSecret = env.getProperty("google.appSecret");
		String redirectUri = env.getProperty("google.redirectUri");
		String scope = env.getProperty("google.scope");
		
		GoogleConnectionFactory factory = new GoogleConnectionFactory(appId, appSecret);
		GooglePlusService gPlusService = new GooglePlusService(factory);
		gPlusService.setRedirectUri(redirectUri);
		gPlusService.setScope(scope);
		gPlusService.setState(RandomStringUtils.randomAlphanumeric(20));
		
		return gPlusService;
	}
	
	@Bean
	public MervidService mervidService() {
		String appId = env.getProperty("mervid.appId");
		String appSecret = env.getProperty("mervid.appSecret");
		String redirectUri = env.getProperty("mervid.redirectUri");
		String scope = env.getProperty("mervid.scope");
		
		MervidConnectionFactory factory = new MervidConnectionFactory(appId, appSecret);
		MervidService mervidService = new MervidService(factory);
		mervidService.setRedirectUri(redirectUri);
		mervidService.setScope(scope);
		mervidService.setState(RandomStringUtils.randomAlphanumeric(20));
		
		return mervidService;
	}
	
	@Bean
	public LinkedInService linkedInService() {
		String appId = env.getProperty("linkedin.appId");
		String appSecret = env.getProperty("linkedin.appSecret");
		String redirectUri = env.getProperty("linkedin.redirectUri");
		String scope = env.getProperty("linkedin.scope");
		
		LinkedInConnectionFactory factory = new LinkedInConnectionFactory(appId, appSecret);
		LinkedInService linkedInService = new LinkedInService(factory);
		linkedInService.setRedirectUri(redirectUri);
		linkedInService.setScope(scope);
		linkedInService.setState(RandomStringUtils.randomAlphanumeric(20));
		
		return linkedInService;
	}
	
	@Bean
	public GithubService githubService() {
		String appId = env.getProperty("github.appId");
		String appSecret = env.getProperty("github.appSecret");
		String redirectUri = env.getProperty("github.redirectUri");
		String scope = env.getProperty("github.scope");
		
		GitHubConnectionFactory factory = new GitHubConnectionFactory(appId, appSecret);
		GithubService githubService = new GithubService(factory);
		githubService.setRedirectUri(redirectUri);
		githubService.setState(RandomStringUtils.randomAlphanumeric(20));
		
		return githubService;
	}

	@Override
	public void setEnvironment(Environment env) {
		this.env = new RelaxedPropertyResolver(env, "social.");
	}
}
