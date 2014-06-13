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
import org.meruvian.yama.service.social.SocialUsersConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.util.MultiValueMap;

/**
 * @author Dian Aditya
 *
 */
public class GoogleManager implements SocialManager<Google> {

	private ConnectionFactoryLocator factoryLocator;
	private PasswordEncoder passwordEncoder;
	private SocialUsersConnectionManager usersConnectionManager;

	@Value("${social.google.redirectUri}") 
	private String redirectUri;
	
	@Value("${social.google.scope}") 
	private String scope;
	
	@Inject
	public void setFactoryLocator(ConnectionFactoryLocator factoryLocator) {
		this.factoryLocator = factoryLocator;
	}

	@Inject
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@Inject
	public void setUsersConnectionManager(SocialUsersConnectionManager usersConnectionManager) {
		this.usersConnectionManager = usersConnectionManager;
	}
	
	@Override
	public String getProviderName() {
		return Provider.GOOGLE.toString();
	}
	
	@Override
	public ConnectionFactory<Google> getConnectionFactory() {
		return factoryLocator.getConnectionFactory(Google.class);
	}
	
	@Override
	public OAuth2Operations getOAuth2Operations() {
		GoogleConnectionFactory factory = (GoogleConnectionFactory) getConnectionFactory();
		return factory.getOAuthOperations();
	}

	@Override
	public User createUser(Connection<?> connection) {
		Google google = (Google) connection.getApi();
		Person profile = google.plusOperations().getGoogleProfile();
		
		String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String randomUsername = RandomStringUtils.random(8, alphanumeric);
		
		JpaUser user = new JpaUser();
		user.setUsername(profile.getGivenName()+profile.getFamilyName()+randomUsername);
		user.getName().setFirst(profile.getGivenName());
		user.getName().setLast(profile.getFamilyName());
		user.setEmail(profile.getEmailAddresses().iterator().next());
		
		String password = RandomStringUtils.random(8, alphanumeric);
		user.setPassword(passwordEncoder.encode(password));
		
		return user;
	}

	@Override
	public String getAuthorizeUrl() {
		GoogleConnectionFactory factory = (GoogleConnectionFactory) getConnectionFactory();
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(redirectUri);
		parameters.setScope(scope);
		parameters.setState("yama");
		
		String authUrl = factory.getOAuthOperations()
				.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, parameters);
		
		return authUrl;
	}

	@Override
	public Connection<Google> createConnection(String authorizationCode, MultiValueMap<String, String> additionalParameters) {
		AccessGrant grant = getOAuth2Operations()
				.exchangeForAccess(authorizationCode, redirectUri, additionalParameters);
		
		return ((GoogleConnectionFactory) getConnectionFactory()).createConnection(grant);
	}

	@Override
	public SocialUsersConnectionManager getUsersConnectionManager() {
		return usersConnectionManager;
	}

	@Override
	public boolean isAuthorized(Connection<?> connection) {
		Google google = (Google) connection.getApi();
		
		return google.isAuthorized();
	}
	
}
