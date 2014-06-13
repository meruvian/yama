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

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Dian Aditya
 *
 */
public class DefaultSessionCredential implements SessionCredential {
	private UserManager userService;
	private UserDetailsService userDetailsService;
	
	@Inject
	public DefaultSessionCredential(UserManager userService) {
		this.userService = userService;
	}
	
	@Inject
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public org.meruvian.yama.repository.user.User getCurrentUser() {
		if (getCurrentUsername() != null)
			return userService.getUserByUsername(getCurrentUsername());
		
		return null;
	}

	@Override
	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		if (authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();
			return user.getUsername();
		}
		
		return null;
	}

	@Override
	public void registerAuthentication(String userId) {
		org.meruvian.yama.repository.user.User user = userService.getUserById(userId);
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}
