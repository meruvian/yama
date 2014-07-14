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
package org.meruvian.yama.service.security.oauth2;

import java.util.Arrays;

import javax.inject.Inject;

import org.meruvian.yama.repository.application.Application;
import org.meruvian.yama.service.ApplicationManager;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @author Dian Aditya
 *
 */
public class DefaultClientDetailsService implements ClientDetailsService {
	private ApplicationManager applicationManager;

	@Inject
	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Application application = applicationManager.getApplicationById(clientId);
		if (application == null) return null;
		
		BaseClientDetails details = new BaseClientDetails();
		details.setClientId(application.getId());
		details.setClientSecret(application.getSecret());
		details.setAuthorizedGrantTypes(Arrays.asList("password", "authorization_code", "refresh_token", "implicit"));
		details.setScope(Arrays.asList("read", "write", "trust"));
		details.setResourceIds(Arrays.asList("yama"));

		return details;
	}

}
