#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright 2012 Meruvian Foundation
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

import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import org.meruvian.yama.security.user.BackendUser;
import org.meruvian.yama.security.user.BackendUserDAO;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

	@Inject
	private BackendUserDAO dao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		BackendUser user = dao.getUserByUsername(username);

		if (user != null) {
			UserDetails details = new User(user.getUsername(),
					user.getPassword(),
					Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

			return details;
		}

		return null;
	}

}
