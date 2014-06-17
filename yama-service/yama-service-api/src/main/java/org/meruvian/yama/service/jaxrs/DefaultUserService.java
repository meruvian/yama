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
package org.meruvian.yama.service.jaxrs;

import javax.inject.Inject;

import org.jboss.resteasy.annotations.Form;
import org.meruvian.yama.repository.role.DefaultRole;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.DefaultUser;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class DefaultUserService implements UserService {
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
		DefaultUser user = new DefaultUser();
		user.setUsername(username);
		
		return userManager.removeUser(user);
	}

	@Override
	public User saveUser(@Form User user) {
		return userManager.saveUser(user);
	}

	@Override
	public User updateUserPassword(String username, String currentPassword, String newPassword) {
		DefaultUser user = new DefaultUser();
		user.setUsername(username);
		
		return userManager.updateUserPassword(user, currentPassword, newPassword);
	}

	@Override
	public boolean addRoleToUser(String username, String roleName) {
		DefaultUser user = new DefaultUser();
		user.setUsername(username);
		
		DefaultRole role = new DefaultRole();
		role.setName(roleName);
		
		return userManager.addRoleToUser(user, role);
	}

	@Override
	public boolean removeRoleFromUser(String username, String roleName) {
		DefaultUser user = new DefaultUser();
		user.setUsername(username);
		
		DefaultRole role = new DefaultRole();
		role.setName(roleName);
		
		return userManager.removeRoleFromUser(user, role);
	}

	@Override
	public Page<? extends Role> findRoleByUser(String username, int max, int page) {
		DefaultUser user = new DefaultUser();
		user.setUsername(username);
		
		return userManager.findRoleByUser(user, new PageRequest(page, max));
	}
}