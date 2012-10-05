/**
 * Copyright 2012 Meruvian
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
package org.meruvian.yama.user.service;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.security.User;

/**
 * @author Edy Setiawan
 * 
 */
public interface UserService {
	User saveUser(User user);

	User getUserById(String id);

	User getUserByUsername(String username);

	User getUserByEmail(String email);
	
	void deleteUser(User user);

	EntityListWrapper<User> findUsers(String username, String nameFirst,
			String nameLast, String company, String position, String orderby,
			String order, int max, int page);

	EntityListWrapper<User> findUsers(String field, int max, String orderBy,
			String order, int page);
}
