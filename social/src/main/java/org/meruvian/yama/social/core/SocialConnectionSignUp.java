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

import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserRepository;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * @author Dian Aditya
 *
 */
public class SocialConnectionSignUp implements ConnectionSignUp {
	private SocialServiceLocator socialServiceLocator;
	private UserRepository userRepository;
	
	public void setSocialServiceLocator(SocialServiceLocator socialServiceLocator) {
		this.socialServiceLocator = socialServiceLocator;
	}
	
	@Override
	public String execute(Connection<?> connection) {
		SocialService<?> socialService = socialServiceLocator.getSocialService(connection.getKey().getProviderId());
		User createdUser = socialService.createUser(connection);
		User user = userRepository.findByEmail(createdUser.getEmail());
		
		if (user != null) {
			createdUser = user;
		} else {
			createdUser = userRepository.save(createdUser);
		}
		
		return createdUser.getId();
	}
}
