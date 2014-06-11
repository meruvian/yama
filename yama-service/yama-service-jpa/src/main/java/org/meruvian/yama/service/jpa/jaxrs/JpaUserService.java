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
package org.meruvian.yama.service.jpa.jaxrs;

import javax.inject.Inject;

import org.jboss.resteasy.annotations.Form;
import org.meruvian.yama.repository.jpa.role.JpaRole;
import org.meruvian.yama.repository.jpa.user.JpaUser;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.UserManager;
import org.meruvian.yama.service.jaxrs.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class JpaUserService implements UserService {
	private UserManager userManager;
	
	@Inject
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Override
	public User getUserByUsername(String username) {
		return userManager.getUserByUsername(username);
	}

	@Override
	public Page<? extends User> findUserByKeyword(String keyword, int max, int page) {
		return userManager.findActiveUserByKeyword(keyword, new PageRequest(page, max));
	}

	@Override
	public boolean removeUser(String username) {
		return userManager.removeUser(new JpaUser(username, null, null, null));
	}

	@Override
	public User saveUser(@Form User user) {
		return userManager.saveUser(user);
	}

	@Override
	public User updateUserPassword(String username, String currentPassword, String newPassword) {
		return userManager.updateUserPassword(new JpaUser(username, null, null, null),
				currentPassword, newPassword);
	}

	@Override
	public boolean addRoleToUser(String username, String roleName) {
		return userManager.addRoleToUser(new JpaUser(username, null, null, null),
				new JpaRole(roleName, null));
	}

	@Override
	public boolean removeRoleFromUser(String username, String roleName) {
		return userManager.removeRoleFromUser(new JpaUser(username, null, null, null),
				new JpaRole(roleName, null));
	}

	@Override
	public Page<? extends Role> findRoleByUser(String username, int max, int page) {
		return userManager.findRoleByUser(new JpaUser(username, null, null, null),
				new PageRequest(page, max));
	}
}