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
package org.meruvian.yama.social.core;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.springframework.social.ApiBinding;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.util.MultiValueMap;

/**
 * @author Dian Aditya
 *
 */
public abstract class AbstractSocialManager<T> implements SocialManager<T> {
	protected SocialUsersConnectionManager usersConnectionManager;
	protected OAuth2ConnectionFactory<T> connectionFactory;
	
	protected String redirectUri;
	protected String scope;
	
	@PostConstruct
	public void postConstruct() {
		Validate.notNull(usersConnectionManager, "UsersConnectionManager must be provided");
		Validate.notNull(connectionFactory, "ConnectionFactory must be provided");
	}
	
	public void setUsersConnectionManager(SocialUsersConnectionManager usersConnectionManager) {
		this.usersConnectionManager = usersConnectionManager;
	}
	
	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public AbstractSocialManager(OAuth2ConnectionFactory<T> connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	@Override
	public Connection<T> createConnection(String authorizationCode, MultiValueMap<String, String> additionalParameters) {
		AccessGrant grant = connectionFactory.getOAuthOperations()
				.exchangeForAccess(authorizationCode, getParameters().getRedirectUri(), additionalParameters);
		
		return connectionFactory.createConnection(grant);
	}

	@Override
	public String getAuthorizeUrl() {
		String authUrl = connectionFactory.getOAuthOperations()
				.buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, getParameters());
		
		return authUrl;
	}

	@Override
	public SocialUsersConnectionManager getUsersConnectionManager() {
		return usersConnectionManager;
	}
	
	@Override
	public boolean isAuthorized(Connection<?> connection) {
		Object api = connection.getApi();
		if (api instanceof ApiBinding) {
			ApiBinding apiBinding = (ApiBinding) api;
			return apiBinding.isAuthorized();
		}
		
		return false;
	}
	
	@Override
	public OAuth2ConnectionFactory<T> getConnectionFactory() {
		return connectionFactory;
	}
	
	@Override
	public OAuth2Parameters getParameters() {
		OAuth2Parameters parameters = new OAuth2Parameters();
		parameters.setRedirectUri(redirectUri);
		parameters.setScope(scope);
		parameters.setState("yama");
		
		return parameters;
	}
}
