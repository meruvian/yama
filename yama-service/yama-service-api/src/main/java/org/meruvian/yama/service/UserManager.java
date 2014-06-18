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
package org.meruvian.yama.service;

import org.meruvian.yama.repository.commons.FileInfo;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dian Aditya
 * 
 */
public interface UserManager {
	User getUserById(String id);

	User getUserByUsername(String username);
	
	User getUserByEmail(String email);

	Page<? extends User> findUserByKeyword(String keyword, Pageable pageable);
	
	Page<? extends User> findActiveUserByKeyword(String keyword, Pageable pageable);

	boolean removeUser(User user);

	User saveUser(User user);

	User updateUserPassword(User user, String currentPassword, String newPassword);
	
	boolean addRoleToUser(User user, Role role);
	
	boolean removeRoleFromUser(User user, Role role);
	
	Page<? extends Role> findRoleByUser(User user, Pageable pageable);

	User findUser(User user);
	
	FileInfo setUserProfilePicture(User user, FileInfo fileInfo);
	
	User updateStatus(User user, int status);
}
