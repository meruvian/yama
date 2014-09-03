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
package org.meruvian.yama.core.application;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.DefaultJpaPersistence;
import org.meruvian.yama.core.JpaLogInformation;

/**
 * @author Dian Aditya
 *
 */
@Entity
@Table(name = "yama_application", indexes = { @Index(columnList = "create_by", unique = false) })
public class JpaApplication extends DefaultJpaPersistence implements Application {
	private String secret;
	private String namespace;
	private String displayName;
	private String domain;
	private String site;
	private String redirectUris;
	private String scopes;
	private String authorizedGrantTypes;
	private boolean autoApprove = false;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private String resourceIds;
	
	public JpaApplication() {}
	
	public JpaApplication(String id) {
		this.id = id;
	}
	
	public JpaApplication(Application application) {
		update(application);
	}
	
	@Override
	public String getSecret() {
		return secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	@Column(unique = true)
	public String getNamespace() {
		return namespace;
	}
	
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@Override
	@Column(name = "display_name")
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

	@Column(name = "registered_redirect_uri")
	public String getRegisteredRedirectUri() {
		return StringUtils.join(getRegisteredRedirectUris(), ',');
	}
	
	protected void setRegisteredRedirectUri(String redirectUri) {
		this.redirectUris = redirectUri;
	}
	
	@Override
	@Transient
	public Set<String> getRegisteredRedirectUris() {
		String[] redirectUris = StringUtils.split(this.redirectUris, ',');
		if (redirectUris == null) return new LinkedHashSet<String>();
		
		return new LinkedHashSet<String>(Arrays.asList(redirectUris));
	}
	
	public void setRegisteredRedirectUri(Set<String> uris) {
		this.redirectUris = StringUtils.join(uris, ',');
	}
	
	@Column(name = "scope")
	public String getScope() {
		return StringUtils.join(getScopes(), ',');
	}
	
	protected void setScope(String scope) {
		this.scopes = scope;
	}
	
	@Override
	@Transient
	public Set<String> getScopes() {
		String[] scopes = StringUtils.split(this.scopes, ',');
		if (scopes == null) return new LinkedHashSet<String>();
		
		return new LinkedHashSet<String>(Arrays.asList(scopes));
	}
	
	public void setScopes(Set<String> scopes) {
		this.scopes = StringUtils.join(scopes, ',');
	}
	
	@Column(name = "authorized_grant_type")
	public String getAuthorizedGrantType() {
		return StringUtils.join(getAuthorizedGrantTypes(), ',');
	}
	
	protected void setAuthorizedGrantType(String authorizedGrantType) {
		this.authorizedGrantTypes = authorizedGrantType;
	}
	
	@Override
	@Transient
	public Set<String> getAuthorizedGrantTypes() {
		String[] authorizedGrantTypes = StringUtils.split(this.authorizedGrantTypes, ',');
		if (authorizedGrantTypes == null) return new LinkedHashSet<String>();
		
		return new LinkedHashSet<String>(Arrays.asList(authorizedGrantTypes));
	}
	
	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = StringUtils.join(authorizedGrantTypes, ',');
	}
	
	@Override
	@Column(name = "auto_approve")
	public boolean isAutoApprove() {
		return this.autoApprove;
	}
	
	public void setAutoApprove(Boolean autoApprove) {
		if (autoApprove == null) autoApprove = false;
		
		this.autoApprove = autoApprove;
	}

	@Override
	@Column(name = "access_token_validity_seconds")
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}
	
	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@Override
	@Column(name = "refresh_token_validity_seconds")
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}
	
	public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}
	
	@Column(name = "resource_id")
	public String getResourceId() {
		return StringUtils.join(getResourceIds(), ',');
	}
	
	protected void setResourceId(String resourceId) {
		this.resourceIds = resourceId;
	}

	@Override
	@Transient
	public Set<String> getResourceIds() {
		String[] resourceIds = StringUtils.split(this.resourceIds, ',');
		if (resourceIds == null) return new LinkedHashSet<String>();
		
		return new LinkedHashSet<String>(Arrays.asList(resourceIds));
	}
	
	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = StringUtils.join(resourceIds, ',');
	}
	
	@Override
	public void update(Application application) {
		this.id = application.getId();
		this.logInformation = new JpaLogInformation(application.getLogInformation());
		this.secret = application.getSecret();
		this.namespace = application.getNamespace();
		this.displayName = application.getDisplayName();
		this.domain = application.getDomain();
		this.site = application.getSite();
		this.setRegisteredRedirectUri(application.getRegisteredRedirectUris());
		this.setScopes(application.getScopes());
		this.setAuthorizedGrantTypes(application.getAuthorizedGrantTypes());
		this.autoApprove = application.isAutoApprove();
		this.accessTokenValiditySeconds = application.getAccessTokenValiditySeconds();
		this.refreshTokenValiditySeconds = application.getRefreshTokenValiditySeconds();
		this.setResourceIds(application.getResourceIds());
	}
}