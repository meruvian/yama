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
package org.meruvian.yama.social.mervid.connect;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.mervid.api.Mervid;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

/**
 * @author Dian Aditya
 *
 */
public class MervidAdapter implements ApiAdapter<Mervid>{

	@Override
	public boolean test(Mervid api) {
		return false;
	}

	@Override
	public void setConnectionValues(Mervid api, ConnectionValues values) {
		User user = api.userOperations().getUser();
		values.setProviderUserId(user.getId());
		values.setDisplayName(user.getUsername());
		values.setProfileUrl(StringUtils.join("http://www.merv.id/admin/users/", user.getUsername()));
	}

	@Override
	public UserProfile fetchUserProfile(Mervid api) {
		User user = api.userOperations().getUser();
		
		return new UserProfileBuilder().setName(user.getUsername())
				.setFirstName(user.getName().getFirst()).setLastName(user.getName().getLast())
				.setEmail(user.getEmail()).setUsername(user.getUsername()).build();
	}

	@Override
	public void updateStatus(Mervid api, String message) {
	}

}
