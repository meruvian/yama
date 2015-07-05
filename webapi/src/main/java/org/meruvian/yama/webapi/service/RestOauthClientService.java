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
package org.meruvian.yama.webapi.service;

import javax.inject.Inject;

import org.meruvian.yama.core.application.Application;
import org.meruvian.yama.core.application.ApplicationRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class RestOauthClientService implements OauthClientService {
	@Inject
	private ClientDetailsService clientDetailsService;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Override
	public Application findClientDetailsById(String id) {
		ClientDetails clientDetails =  clientDetailsService.loadClientByClientId(id);
		
		if (clientDetails == null) {
			return null;
		}
		
		Application application = applicationRepository.findById(clientDetails.getClientId());
		application.setScopes(clientDetails.getScope());
		application.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
		
		return application;
	}
}
