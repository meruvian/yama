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
package org.meruvian.yama.social.google;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.commons.FileInfo;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.core.AbstractSocialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dian Aditya
 *
 */
public class GooglePlusService extends AbstractSocialService<Google> {
	private static final Logger LOG = LoggerFactory.getLogger(GooglePlusService.class);
	
	private RestTemplate restTemplate;
	
	public GooglePlusService(OAuth2ConnectionFactory<Google> google) {
		super(google);
		restTemplate = new RestTemplate();
	}

	@Override
	public User createUser(Connection<?> connection) {
		Google google = (Google) connection.getApi();
		Person profile = google.plusOperations().getGoogleProfile();
		
		String randomUsername = RandomStringUtils.randomAlphanumeric(6);
		
		User user = new User();
		user.setUsername(StringUtils.join(profile.getGivenName(), profile.getFamilyName(), randomUsername));
		user.getName().setFirst(profile.getGivenName());
		user.getName().setLast(profile.getFamilyName());
		user.setEmail(profile.getEmailAddresses().iterator().next());
		
		user.setPassword(RandomStringUtils.randomAlphanumeric(8));
		
		FileInfo fileInfo = new FileInfo();
		try {
			Resource resource = restTemplate.getForObject(profile.getImageUrl(), Resource.class);
			fileInfo.setDataBlob(resource.getInputStream());
			user.setFileInfo(fileInfo);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return user;
	}
}
