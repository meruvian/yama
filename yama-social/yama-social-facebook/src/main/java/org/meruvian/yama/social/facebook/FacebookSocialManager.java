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
package org.meruvian.yama.social.facebook;

import org.apache.commons.lang3.RandomStringUtils;
import org.meruvian.yama.core.user.DefaultUser;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.core.AbstractSocialManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;

/**
 * @author Dian Aditya
 *
 */
public class FacebookSocialManager extends AbstractSocialManager<Facebook> {
	public FacebookSocialManager(OAuth2ConnectionFactory<Facebook> connectionFactory) {
		super(connectionFactory);
	}

	@Override
	public User createUser(Connection<?> connection) {
		Facebook facebook = (Facebook) connection.getApi();
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		
		String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String randomUsername = RandomStringUtils.random(4, alphanumeric);
		
		DefaultUser user = new DefaultUser();
		user.setUsername(profile.getFirstName()+profile.getLastName()+randomUsername);
		user.getName().setFirst(profile.getFirstName());
		user.getName().setLast(profile.getLastName());
		user.getName().setMiddle(profile.getMiddleName());
		user.setEmail(profile.getEmail());
		
		String password = RandomStringUtils.random(8, alphanumeric);
		user.setPassword(password);
		
		return user;
	}

	@Override
	public boolean isAuthorized(Connection<?> connection) {
		Facebook facebook = (Facebook) connection.getApi();
		
		return facebook.isAuthorized();
	}
}
