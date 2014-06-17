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
package org.meruvian.yama.service.social.google;

import org.apache.commons.lang3.RandomStringUtils;
import org.meruvian.yama.repository.user.DefaultUser;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.social.AbstractSocialManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;

/**
 * @author Dian Aditya
 *
 */
public class GoogleSocialManager extends AbstractSocialManager<Google> {
	public GoogleSocialManager(OAuth2ConnectionFactory<Google> connectionFactory) {
		super(connectionFactory);
	}

	@Override
	public User createUser(Connection<?> connection) {
		Google google = (Google) connection.getApi();
		Person profile = google.plusOperations().getGoogleProfile();
		
		String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String randomUsername = RandomStringUtils.random(8, alphanumeric);
		
		DefaultUser user = new DefaultUser();
		user.setUsername(profile.getGivenName()+profile.getFamilyName()+randomUsername);
		user.getName().setFirst(profile.getGivenName());
		user.getName().setLast(profile.getFamilyName());
		user.setEmail(profile.getEmailAddresses().iterator().next());
		
		String password = RandomStringUtils.random(8, alphanumeric);
		user.setPassword(password);
		
		return user;
	}

	@Override
	public boolean isAuthorized(Connection<?> connection) {
		Google google = (Google) connection.getApi();
		
		return google.isAuthorized();
	}
}
