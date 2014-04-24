/**
 * Copyright 2012 BlueOxygen Technology
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
package org.meruvian.yama.security;

import java.util.Date;

import javax.inject.Inject;

import org.meruvian.yama.persistence.access.PersistenceDAO;
import org.meruvian.yama.persistence.access.PersistenceManager;
import org.meruvian.yama.security.user.BackendUser;
import org.meruvian.yama.security.user.BackendUserDAO;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Service
@Transactional(readOnly = true)
public class BackendUserService extends PersistenceManager<BackendUser> {
	@Inject
	private BackendUserDAO dao;

	@Inject
	private PasswordEncoder encoder;

	public BackendUser loadBackendUserByUsername(String username)
			throws UsernameNotFoundException {

		BackendUser user = dao.getUserByUsername(username);

		return user;
	}

	@Transactional
	public void initUser() {
		BackendUser user = new BackendUser();
		user.setUsername("admin");
		user.setPassword(encoder.encodePassword("admin", null));
		user.setRole("ADMINISTRATOR");

		saveBackenUser(user);
	}

	@Transactional
	public BackendUser saveBackenUser(BackendUser user) {
		if (user.getId() == null) {
			dao.persist(user);
		} else {
			BackendUser temp = dao.findById(user.getId());
			temp.getLogInformation().setUpdateBy(user.getId());
			temp.getLogInformation().setUpdateDate(new Date());
			temp.setUsername(user.getUsername());
			temp.setEmail(user.getEmail());

			return dao.merge(temp);
		}

		return user;
	}

	@Transactional
	public BackendUser changePassword(String username, String newPassword) {
		BackendUser user = dao.getUserByUsername(username);
		user.setPassword(encoder.encodePassword(newPassword, null));
		user.getLogInformation().setUpdateBy(user.getId());
		user.getLogInformation().setUpdateDate(new Date());

		dao.persist(user);

		return user;
	}

	@Override
	protected PersistenceDAO<BackendUser> getDao() {
		return dao;
	}
}
