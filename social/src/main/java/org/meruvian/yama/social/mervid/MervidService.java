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
package org.meruvian.yama.social.mervid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.LogInformation;
import org.meruvian.yama.core.commons.Address;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.core.AbstractSocialService;
import org.meruvian.yama.social.mervid.api.Mervid;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author Dian Aditya
 *
 */
public class MervidService extends AbstractSocialService<Mervid>{
	public MervidService(OAuth2ConnectionFactory<Mervid> connectionFactory) {
		super(connectionFactory);
	}

	@Override
	public User createUser(Connection<?> connection) {
		Mervid mervid = (Mervid) connection.getApi();
		
		String randomUsername = RandomStringUtils.randomAlphanumeric(6);
		
		User user = mervid.userOperations().getUser();
		user.setId(null);
		user.setAddress(new Address());
		user.setLogInformation(new LogInformation());
		user.setFileInfo(null);
		
		user.setUsername(StringUtils.join(user.getName().getFirst(), user.getName().getLast(), randomUsername));
		user.setPassword(RandomStringUtils.randomAlphanumeric(8));
		
		return user;
	}
}
