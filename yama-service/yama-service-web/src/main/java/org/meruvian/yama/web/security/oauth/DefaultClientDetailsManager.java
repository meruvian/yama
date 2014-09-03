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
package org.meruvian.yama.web.security.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.meruvian.yama.core.application.Application;
import org.meruvian.yama.core.application.ApplicationManager;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @author Dian Aditya
 *
 */
public class DefaultClientDetailsManager implements ClientDetailsService {
	private ApplicationManager applicationManager;
	private Collection<String> authorizedGrantTypes;
	private Collection<String> scopes;
	private Collection<String> resourceIds = new ArrayList<String>();
	
	@PostConstruct
	public void postConstruct() {
		Validate.notNull(applicationManager, "ApplicationManager must be provided");
		
		if (authorizedGrantTypes == null) {
			authorizedGrantTypes = new ArrayList<String>();
			authorizedGrantTypes.add("authorization_code");
			authorizedGrantTypes.add("refresh_token");
			authorizedGrantTypes.add("implicit");
			authorizedGrantTypes.add("password");
		}
		
		if (scopes == null) {
			scopes = new ArrayList<String>();
			scopes.add("read");
			scopes.add("write");
			scopes.add("trust");
		}
	}
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Application application = applicationManager.getApplicationById(clientId);
		if (application == null) return null;
		
		BaseClientDetails details = new BaseClientDetails();
		details.setClientId(application.getId());
		details.setClientSecret(application.getSecret());
		details.setAuthorizedGrantTypes(authorizedGrantTypes);
		details.setScope(scopes);
		details.setResourceIds(resourceIds);
		details.setRegisteredRedirectUri(application.getRegisteredRedirectUris());
		if (application.isAutoApprove())
			details.setAutoApproveScopes(Arrays.asList("true"));
		details.setAccessTokenValiditySeconds(application.getAccessTokenValiditySeconds());
		details.setRefreshTokenValiditySeconds(application.getRefreshTokenValiditySeconds());
		
		return details;
	}

	public void setAuthorizedGrantTypes(Collection<String> authoritizedGrantTypes) {
		this.authorizedGrantTypes = authoritizedGrantTypes;
	}
	
	public void setScopes(Collection<String> scopes) {
		this.scopes = scopes;
	}
	
	public void setResourceIds(Collection<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}
}
