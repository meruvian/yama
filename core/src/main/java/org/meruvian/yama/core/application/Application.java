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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.DefaultPersistence;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dian Aditya
 *
 */
@Entity
@Table(name = "yama_application", indexes = { @Index(columnList = "create_by", unique = false) })
public class Application extends DefaultPersistence {
	public enum GrantType {
		AUTHORIZATION_CODE, IMPLICIT, PASSWORD, REFRESH_TOKEN
	}
	
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

	@NotNull
	@Lob
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(unique = true, nullable = true)
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@NotNull
	@Column(name = "display_name")
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@NotNull
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@NotNull
	@JsonIgnore
	@Column(name = "registered_redirect_uri")
	public String getRegisteredRedirectUri() {
		return redirectUris;
	}

	public void setRegisteredRedirectUri(String redirectUri) {
		this.redirectUris = redirectUri;
	}

	@Transient
	public Set<String> getRegisteredRedirectUris() {
		String[] redirectUris = StringUtils.split(this.redirectUris, ',');
		if (redirectUris == null)
			return new LinkedHashSet<String>();

		return new LinkedHashSet<String>(Arrays.asList(redirectUris));
	}

	public void setRegisteredRedirectUris(Set<String> uris) {
		this.redirectUris = StringUtils.join(uris, ',');
	}

	@Column(name = "scope")
	public String getScope() {
		return scopes;
	}

	protected void setScope(String scope) {
		this.scopes = scope;
	}

	@Transient
	public Set<String> getScopes() {
		String[] scopes = StringUtils.split(this.scopes, ',');
		if (scopes == null)
			return new LinkedHashSet<String>();

		return new LinkedHashSet<String>(Arrays.asList(scopes));
	}

	public void setScopes(Set<String> scopes) {
		this.scopes = StringUtils.join(scopes, ',');
	}

	@JsonIgnore
	@Column(name = "authorized_grant_type")
	public String getAuthorizedGrantType() {
		return authorizedGrantTypes;
	}

	protected void setAuthorizedGrantType(String authorizedGrantType) {
		this.authorizedGrantTypes = authorizedGrantType;
	}

	@Transient
	public Set<String> getAuthorizedGrantTypes() {
		String[] authorizedGrantTypes = StringUtils.split(this.authorizedGrantTypes, ',');
		if (authorizedGrantTypes == null)
			return new LinkedHashSet<String>();

		return new LinkedHashSet<String>(Arrays.asList(authorizedGrantTypes));
	}

	public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
		this.authorizedGrantTypes = StringUtils.join(authorizedGrantTypes, ',');
	}

	@Column(name = "auto_approve")
	public boolean isAutoApprove() {
		return this.autoApprove;
	}

	public void setAutoApprove(Boolean autoApprove) {
		if (autoApprove == null)
			autoApprove = false;

		this.autoApprove = autoApprove;
	}

	@Column(name = "access_token_validity_seconds")
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}

	public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
	}

	@Column(name = "refresh_token_validity_seconds")
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(
			Integer refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	@JsonIgnore
	@Column(name = "resource_id")
	public String getResourceId() {
		return StringUtils.join(getResourceIds(), ',');
	}

	protected void setResourceId(String resourceId) {
		this.resourceIds = resourceId;
	}

	@Transient
	public Set<String> getResourceIds() {
		String[] resourceIds = StringUtils.split(this.resourceIds, ',');
		if (resourceIds == null)
			return new LinkedHashSet<String>();

		return new LinkedHashSet<String>(Arrays.asList(resourceIds));
	}

	public void setResourceIds(Set<String> resourceIds) {
		this.resourceIds = StringUtils.join(resourceIds, ',');
	}
}