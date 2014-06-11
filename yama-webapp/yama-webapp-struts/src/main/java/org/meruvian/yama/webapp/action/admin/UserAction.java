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
package org.meruvian.yama.webapp.action.admin;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.yama.repository.jpa.role.JpaRole;
import org.meruvian.yama.repository.jpa.user.JpaUser;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.RoleManager;
import org.meruvian.yama.service.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/admin/users")
public class UserAction {
	@Inject
	private UserManager userManager;
	
	@Inject
	private RoleManager roleManager;
	
	@Action(method = HttpMethod.GET)
	public ActionResult userList(@ActionParam("q") String q, @ActionParam("max") int max,
			@ActionParam("page") int page) {
		max = max == 0 ? 10 : max;
		Page<? extends User> users = userManager.findActiveUserByKeyword(q, new PageRequest(page, max));
		
		return new ActionResult("freemarker","/view/admin/user/user-list.ftl")
				.addToModel("users", users);
	}
	
	@Action(name = "/{username}/edit", method = HttpMethod.GET)
	public ActionResult userForm(@ActionParam("username") String username) {
		ActionResult actionResult = new ActionResult("freemarker", "/view/admin/user/user-form.ftl");
		
		Page<? extends Role> roles = roleManager.findActiveRoleByKeyword("", null);
		actionResult.addToModel("roles", roles);
		
		if (!StringUtils.equalsIgnoreCase(username, "-")) {
			User user = userManager.getUserByUsername(username);
			actionResult.addToModel("user", user);
			
			Page<? extends Role> userRoles = userManager.findRoleByUser(user, null);
			actionResult.addToModel("userRoles", userRoles);
		}
		
		return actionResult;
	}
	
	@Action(name = "/{username}/edit", method = HttpMethod.POST)
	public ActionResult updateUser(@ActionParam("username") String username, 
			@ActionParam("user") JpaUser user, @ActionParam("roles") String[] roles) {
		User u = userManager.saveUser(user);
		String redirectUri = "/admin/users/" + u.getUsername() + "/edit?success";
		
		if (StringUtils.equalsIgnoreCase(username, "-")) {
			redirectUri = "/admin/users?success";
		} else {
			for (Role r : userManager.findRoleByUser(u, null)) {
				userManager.removeRoleFromUser(u, r);
			}
		}
		
		for (String r : roles) {
			userManager.addRoleToUser(u, new JpaRole(r, null));
		}
		
		return new ActionResult("redirect", redirectUri);
	}
}
