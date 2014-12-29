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
package org.meruvian.yama.webapi.service;

import javax.inject.Inject;

import org.meruvian.yama.core.role.UserRole;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserRepository;
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
	private UserRepository userRepository;
	
	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Page<User> findUserByKeyword(String keyword, int max, int page) {
		return userRepository.findByUsernameStartingWith(keyword, new PageRequest(page, max));
	}

	@Override
	public void removeUser(final String username) {
		userRepository.delete(getUserByUsername(username));
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUserPassword(final String username, String currentPassword, String newPassword) {
//		return userManager.updateUserPassword(new DefaultUser() {{ setUsername(username); }}, currentPassword, newPassword);
		
		return null;
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
	public Page<UserRole> findRoleByUser(final String username, int max, int page) {
		return null;
//		return userManager.findRoleByUser(new DefaultUser() {{ setUsername(username); }}, new PageRequest(page, max));
	}

}
