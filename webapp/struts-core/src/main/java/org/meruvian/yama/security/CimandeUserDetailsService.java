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
package org.meruvian.yama.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.roleprivilege.service.RolePrivilegeService;
import org.meruvian.yama.user.service.UserService;
import org.meruvian.yama.userprivilege.service.UserPrivilegeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Transactional(readOnly = true)
public class CimandeUserDetailsService extends
		SavedRequestAwareAuthenticationSuccessHandler implements
		UserDetailsService, AuthenticationSuccessHandler {
	@Inject
	private UserService userService;

	@Inject
	private UserPrivilegeService privilegeService;

	@Inject
	private RolePrivilegeService rolePrivilegeService;

	public CimandeUserDetailsService() {
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		org.meruvian.yama.security.User user = userService
				.getUserByUsername(username);
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for (Role role : privilegeService.findRoleByUser(user.getId(),
					null, "ASC", 0, 1).getEntityList()) {
				authorities.add(new SimpleGrantedAuthority(role.getId()));
			}

			UserDetails details = new User(user.getUsername(),
					user.getPassword(), authorities);

			return details;
		}

		return null;
	}

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		org.meruvian.yama.security.User backend = userService
				.getUserByUsername(user.getUsername());
		List<Role> roles = privilegeService.findRoleByUser(backend.getId(),
				null, "ASC", 1, 0).getEntityList();

		HttpSession session = request.getSession();
		session.setAttribute(SessionCredentials.CIMANDE_SECURITY_USER, backend);
		session.setAttribute(SessionCredentials.SPRING_SECURITY_USER, user);

		Role currentRole = null;

		if (roles.size() > 0) {
			currentRole = roles.get(0);
			session.setAttribute(SessionCredentials.CIMANDE_SECURITY_ROLE,
					currentRole);
		}

		// if (redirectUrl.startsWith("/")) {
		// response.sendRedirect(request.getContextPath() + redirectUrl);
		// } else {
		// response.sendRedirect(redirectUrl);
		// }

		if (StringUtils.isBlank(request.getParameter("redirectUri"))) {
			super.onAuthenticationSuccess(request, response, authentication);
		} else {
			setTargetUrlParameter("redirectUri");
			handle(request, response, authentication);
		}
	}
}
