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
package org.meruvian.yama.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.meruvian.yama.core.user.User;
import org.meruvian.yama.web.security.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Dian Aditya
 *
 */
public class SessionCredentials {
	public static User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		if (authentication.getPrincipal() instanceof DefaultUserDetails) {
			DefaultUserDetails user = (DefaultUserDetails) authentication.getPrincipal();
			return user.getUser();
		}
		
		if (authentication.getPrincipal() instanceof String) {
			String principal = (String) authentication.getPrincipal();
			if ("anonymousUser".equals(principal)) {
				return null;
			}
			
			User user = new User();
			user.setUsername((String) authentication.getPrincipal());
			return user;
		}
		
		return null;
	}
	
	public static String getCurrentUsername() {
		org.meruvian.yama.core.user.User user = getCurrentUser();
		
		if (user != null) {
			return user.getUsername();
		}
		
		return null;
	}
	
	public static List<String> getAuthorities() {
		List<String> roles = new ArrayList<String>();
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
		}
		
		return roles;
	}
 }
