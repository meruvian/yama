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
package org.meruvian.yama.service.social;

import org.meruvian.yama.repository.user.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.util.MultiValueMap;

/**
 * @author Dian Aditya
 *
 */
public interface SocialManager<T> {
	String getProviderName();
	
	Connection<T> createConnection(String authorizationCode, MultiValueMap<String, String> additionalParameters);;
	
	User getUser(Connection<?> connection);
	
	String getAuthorizeUrl();
	
	OAuth2Operations getOAuth2Operations();

	ConnectionFactory<T> getConnectionFactory();
}
