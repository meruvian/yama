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

import java.io.ByteArrayInputStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.commons.FileInfo;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.core.AbstractSocialService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.ImageType;

/**
 * @author Dian Aditya
 *
 */
public class FacebookService extends AbstractSocialService<Facebook> {
	public FacebookService(OAuth2ConnectionFactory<Facebook> facebook) {
		super(facebook);
	}

	@Override
	public User createUser(Connection<?> connection) {
		Facebook facebook = (Facebook) connection.getApi();
		FacebookProfile profile = facebook.userOperations().getUserProfile();
		
		String randomUsername = RandomStringUtils.randomAlphanumeric(6);
		
		User user = new User();
		user.setUsername(StringUtils.join(profile.getFirstName(), profile.getLastName(), randomUsername));
		user.getName().setFirst(profile.getFirstName());
		user.getName().setLast(profile.getLastName());
		user.getName().setMiddle(profile.getMiddleName());
		user.setEmail(profile.getEmail());
		
		if (StringUtils.isBlank(profile.getEmail())) {
			user.setEmail(StringUtils.join(profile.getUsername(), "@facebook.com"));
		}
		
		user.setPassword(RandomStringUtils.randomAlphanumeric(8));
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setDataBlob(new ByteArrayInputStream(
				facebook.userOperations().getUserProfileImage(ImageType.NORMAL)));
		user.setFileInfo(fileInfo);
		
		return user;
	}
}
