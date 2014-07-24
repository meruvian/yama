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
package org.meruvian.yama.repository.application;

import java.util.LinkedHashSet;
import java.util.Set;

import org.meruvian.yama.repository.LogInformation;

/**
 * @author Dian Aditya
 * 
 */
public class DefaultApplication implements Application {
	private String id;
	private LogInformation logInformation = new LogInformation();
	private String secret;
	private String namespace;
	private String displayName;
	private String domain;
	private String site;
	private Set<String> redirectUris = new LinkedHashSet<String>();
	private Set<String> scopes = new LinkedHashSet<String>();
	private Set<String> authorizedGrantTypes = new LinkedHashSet<String>();
	private boolean autoApprove = false;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private Set<String> resourceIds = new LinkedHashSet<String>();

	public DefaultApplication() {}
	
	public DefaultApplication(Application application) {
		update(application);
	}
	
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public LogInformation getLogInformation() {
		return logInformation;
	}

	public void setLogInformation(LogInformation logInformation) {
		this.logInformation = logInformation;
	}

	@Override
	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Override
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	@Override
	public Set<String> getRegisteredRedirectUris() {
		return redirectUris;
	}
	
	public void setRegisteredRedirectUris(Set<String> redirectUris) {
		this.redirectUris = redirectUris;
	}
	
	@Override
	public Set<String> getScopes() {
		return scopes;
	}
	
	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}
	
	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}
	
	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}
	
	@Override
	public boolean isAutoApprove() {
		return autoApprove;
	}
	
	public void setAutoApprove(boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}
	
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}
	
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}
	
	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}
	
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	@Override
	public void update(Application application) {
		this.id = application.getId();
		this.logInformation = application.getLogInformation();
		this.secret = application.getSecret();
		this.namespace = application.getNamespace();
		this.displayName = application.getDisplayName();
		this.domain = application.getDomain();
		this.site = application.getSite();
		this.redirectUris = application.getRegisteredRedirectUris();
		this.scopes = application.getScopes();
		this.authorizedGrantTypes = application.getAuthorizedGrantTypes();
		this.autoApprove = application.isAutoApprove();
		this.accessTokenValiditySeconds = application.getAccessTokenValiditySeconds();
		this.refreshTokenValiditySeconds = application.getRefreshTokenValiditySeconds();
		this.resourceIds= application.getResourceIds();
	}
}
