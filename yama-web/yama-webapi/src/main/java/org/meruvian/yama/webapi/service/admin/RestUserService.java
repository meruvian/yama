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
package org.meruvian.yama.webapi.service.admin;

import javax.inject.Inject;

import org.jboss.resteasy.annotations.Form;
import org.meruvian.yama.core.role.UserRole;
import org.meruvian.yama.core.user.DefaultUser;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class RestUserService implements UserService {
	@Inject
	private UserManager userManager;
	
	@Override
	public User getUserByUsername(String username) {
		return userManager.getUserByUsername(username);
	}

	@Override
	public Page<? extends User> findUserByKeyword(String keyword, int max, int page) {
		return userManager.findUserByKeyword(keyword, new PageRequest(page, max));
	}

	@Override
	public boolean removeUser(final String username) {
		return userManager.removeUser(new DefaultUser() {{ setUsername(username); }});
	}

	@Override
	public User saveUser(@Form DefaultUser user) {
		return userManager.saveUser(user);
	}

	@Override
	public User updateUserPassword(final String username, String currentPassword, String newPassword) {
		return userManager.updateUserPassword(new DefaultUser() {{ setUsername(username); }}, currentPassword, newPassword);
	}

	@Override
	public boolean addRoleToUser(String username, String roleName) {
		return false;
	}

	@Override
	public boolean removeRoleFromUser(String username, String roleName) {
		return false;
	}

	@Override
	public Page<? extends UserRole> findRoleByUser(final String username, int max, int page) {
		return userManager.findRoleByUser(new DefaultUser() {{ setUsername(username); }}, new PageRequest(page, max));
	}

}
