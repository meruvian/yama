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
package org.meruvian.yama.service.jpa.social.manager;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.meruvian.yama.repository.jpa.user.JpaUser;
import org.meruvian.yama.repository.social.SocialConnection.Provider;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.social.SocialManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.util.MultiValueMap;

/**
 * @author Dian Aditya
 *
 */
public class FacebookManager implements SocialManager<Facebook> {
	private ConnectionFactoryLocator factoryLocator;
	private PasswordEncoder passwordEncoder;

	@Value("${social.facebook.redirectUri}") 
	private String redirectUri;
	
	@Value("${social.facebook.scope}") 
	private String scope;
	
	@Inject
	public void setFactoryLocator(ConnectionFactoryLocator factoryLocator) {
		this.factoryLocator = factoryLocator;
	}

	@Inject
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public String getProviderName() {
		return Provider.FACEBOOK.toString();
	}
	
	@Override
	public ConnectionFactory<Facebook> getConnectionFactory() {
		return factoryLocator.getConnectionFactory(Facebook.class);
	}
	
	@Override
	public OAuth2Operations getOAuth2Operations() {
		FacebookConnectionFactory factory = (FacebookConnectionFactory) getConnectionFactory();
		return factory.getOAuthOperations();
	}

	@Override
	public User getUser(Connection<?> connection) {
		Facebook facebook = (Facebook) connection.getApi();
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		
		JpaUser user = new JpaUser();
		user.setUsername(null);
		user.getName().setFirst(profile.getFirstName());
		user.getName().setLast(profile.getLastName());
		user.getName().setMiddle(profile.getMiddleName());
		user.setEmail(profile.getEmail());
		
		String password = RandomStringUtils.random(8,
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890");
		user.setPassword(passwordEncoder.encode(password));
		
		return user;
	}

	@Override
	public String getAuthorizeUrl() {
		FacebookConnectionFactory factory = (FacebookConnectionFactory) getConnectionFactory();
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(redirectUri);
		parameters.setScope(scope);
		parameters.setState("yama");
		
		String authUrl = factory.getOAuthOperations()
				.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters);
		
		return authUrl;
	}

	@Override
	public Connection<Facebook> createConnection(String authorizationCode, MultiValueMap<String, String> additionalParameters) {
		AccessGrant grant = getOAuth2Operations()
				.exchangeForAccess(authorizationCode, redirectUri, additionalParameters);
		
		return ((FacebookConnectionFactory) getConnectionFactory()).createConnection(grant);
	}

}
