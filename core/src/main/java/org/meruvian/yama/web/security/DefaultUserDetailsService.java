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
package org.meruvian.yama.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.LogInformation;
import org.meruvian.yama.core.role.Role;
import org.meruvian.yama.core.role.UserRole;
import org.meruvian.yama.core.role.UserRoleRepository;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service("userDetailsService")
public class DefaultUserDetailsService implements UserDetailsService {
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user != null) {
			boolean enabled = user.getLogInformation().getActiveFlag() == LogInformation.ACTIVE;
			
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			Page<? extends UserRole> userRoles = userRoleRepository.findByUserId(user.getId(), null);
			for (UserRole userRole : userRoles) {
				Role role = userRole.getRole();
				authorities.add(new SimpleGrantedAuthority(StringUtils.upperCase(role.getName())));
			}
			
			DefaultUserDetails details = new DefaultUserDetails(user.getUsername(), user.getPassword(), enabled, true, true, true, authorities);
			details.setId(user.getId());
			details.setUser(user);
			
			return details;
		}
		
		return null;
	}
}
