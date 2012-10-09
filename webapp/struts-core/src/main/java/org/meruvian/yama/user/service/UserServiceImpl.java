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

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.meruvian.yama.security.User;
import org.meruvian.yama.user.dao.UserDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Edy Setiawan
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
	@Inject
	private UserDomain userDomain;

	@Transactional
	public User saveUser(User user) {
		
		if (StringUtils.isBlank(user.getId())) {

			user.setId(null);
			user.setLogInformation(new LogInformation());

			userDomain.persist(user);
		} else {
			User s = userDomain.load(user.getId());
			s.setUsername(user.getUsername());
			s.setPassword(user.getPassword());
			s.setName(user.getName());
			s.setAddress(user.getAddress());
			s.setEmail(user.getEmail());
			s.setDescription(user.getDescription());

			s.getLogInformation().setUpdateDate(new Date());

			return s;
		}
		return user;
	}

	public User getUserByUsername(String username) {
		return userDomain.findUser(username);
	}
	

	@Transactional
	public void deleteUser(User user) {
		user = userDomain.load(user.getId());
		user.getLogInformation().setStatusFlag(StatusFlag.INACTIVE);
	}

	public EntityListWrapper<User> findUsers(String username, String nameFirst,
			String nameLast, String company, String position, String orderby,
			String order, int max, int page) {
		return userDomain.findUsers(username, nameFirst, company, position,
				nameLast, orderby, order, max, page, "AND");
	}

	public EntityListWrapper<User> findUsers(String field, int max,
			String orderBy, String order, int page) {
		return userDomain.findUsers(field, field, field, field, field, orderBy,
				order, max, page, "OR");
	}

	public User getUserById(String id) {
		return userDomain.findById(id);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDomain.findUserByEmail(email);
	}

}
