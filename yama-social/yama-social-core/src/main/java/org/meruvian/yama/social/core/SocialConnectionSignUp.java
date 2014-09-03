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
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * @author Dian Aditya
 *
 */
public class SocialConnectionSignUp implements ConnectionSignUp {
	private SocialManagerLocator socialManagerLocator;
	private UserManager userManager;
	
	public void setSocialManagerLocator(SocialManagerLocator socialManagerLocator) {
		this.socialManagerLocator = socialManagerLocator;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@PostConstruct
	public void postConstruct() {
		Validate.notNull(socialManagerLocator, "SocialManagerLocator must be provided");
		Validate.notNull(userManager, "UserManager must be provided");
	}
	
	@Override
	public String execute(Connection<?> connection) {
		SocialManager<?> socialManager = socialManagerLocator.getSocialManager(connection.getKey().getProviderId());
		User createdUser = socialManager.createUser(connection);
		User user = userManager.findUser(createdUser);
		
		if (user != null) {
			createdUser = user;
		} else {
			createdUser = userManager.saveUser(createdUser);
		}
		
		return createdUser.getId();
	}
}
