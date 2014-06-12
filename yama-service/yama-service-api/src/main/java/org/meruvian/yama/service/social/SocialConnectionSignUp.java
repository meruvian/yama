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

import javax.inject.Inject;

import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.UserManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class SocialConnectionSignUp implements ConnectionSignUp {
	private SocialManagerLocator socialManagerLocator;
	private UserManager userManager;
	
	@Inject
	public void setSocialManagerLocator(SocialManagerLocator socialManagerLocator) {
		this.socialManagerLocator = socialManagerLocator;
	}

	@Inject
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Override
	public String execute(Connection<?> connection) {
		SocialManager<?> socialManager = socialManagerLocator.getSocialManager(connection.getKey().getProviderId());
		
//		User user = userManager.saveUser(socialManager.getUser());
		
//		return user.getId();
		
		return null;
	}
}
