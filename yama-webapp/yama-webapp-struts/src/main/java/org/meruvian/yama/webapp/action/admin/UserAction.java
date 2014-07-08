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
import org.apache.commons.validator.routines.EmailValidator;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.yama.repository.role.DefaultRole;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.DefaultUser;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.RoleManager;
import org.meruvian.yama.service.UserManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/admin/users")
public class UserAction extends ActionSupport {
	@Inject
	private UserManager userManager;
	
	@Inject
	private RoleManager roleManager;
	
	@Action(method = HttpMethod.GET)
	public ActionResult userList(@ActionParam("q") String q, @ActionParam("max") int max,
			@ActionParam("page") int page) {
		max = max == 0 ? 10 : max;
		Page<? extends User> users = userManager.findUserByKeyword(q, new PageRequest(page, max));
		
		return new ActionResult("freemarker","/view/admin/user/user-list.ftl")
				.addToModel("users", users);
	}
	
	@Action(name = "/{username}/edit", method = HttpMethod.GET)
	public ActionResult userForm(@ActionParam("username") String username) {
		ActionResult actionResult = new ActionResult("freemarker", "/view/admin/user/user-form.ftl");
		
		showRoles(actionResult, username);
		
		return actionResult;
	}
	
	@Action(name = "/{username}/edit", method = HttpMethod.POST)
	public ActionResult updateUser(@ActionParam("username") String username, 
			@ActionParam("user") DefaultUser user, @ActionParam("roles") String[] roles,
			@ActionParam("confirmPassword") String confirmPassword) {
		validateUser(user, confirmPassword);
		
		if (hasFieldErrors()) {
			ActionResult actionResult = new ActionResult("freemarker", "/view/admin/user/user-form.ftl");
			
			showRoles(actionResult, username);
			
			return actionResult;
		}
		
		User u = userManager.saveUser(user);
		String redirectUri = "/admin/users/" + u.getUsername() + "/edit?success";
		
		if (StringUtils.equalsIgnoreCase(username, "-")) {
			redirectUri = "/admin/users?success";
		} else {
			for (Role r : userManager.findRoleByUser(u, null)) {
				userManager.removeRoleFromUser(u, r);
			}
		}
		
		if (roles != null) {
			for (String r : roles) {
				DefaultRole role = new DefaultRole();
				role.setName(r);
				
				userManager.addRoleToUser(u, role);
			}
		}
		
		return new ActionResult("redirect", redirectUri);
	}
	
	@Action(name = "/{username}/edit/status", method = HttpMethod.POST)
	public ActionResult updateUserStatus(@ActionParam("id") final String id, @ActionParam("status") int status) {
		User u = new DefaultUser() {{ setId(id); }};
		u = userManager.updateStatus(u, status);
		
		String redirectUri = "/admin/users/" + u.getUsername() + "/edit?success";
		
		return new ActionResult("redirect", redirectUri);
	}
	
	public void showRoles(ActionResult actionResult, String username) {
		Page<? extends Role> roles = roleManager.findActiveRoleByKeyword("", null);
		actionResult.addToModel("roles", roles);
		
		if (!StringUtils.equalsIgnoreCase(username, "-")) {
			User user = userManager.getUserByUsername(username);
			actionResult.addToModel("user", user);
			
			Page<? extends Role> userRoles = userManager.findRoleByUser(user, null);
			actionResult.addToModel("userRoles", userRoles);
		}
	}
	
	private void validateUser(DefaultUser user, String confirmPassword) {
		User u = userManager.getUserById(user.getId());
		String username = u == null ? "" : u.getUsername();
		String email = u == null ? "" : u.getEmail();
		
		if (StringUtils.isBlank(user.getUsername())) {
			addFieldError("user.username", getText("message.admin.user.username.notempty"));
		} else {
			if (!StringUtils.equals(username, user.getUsername())) {
				if (userManager.getUserByUsername(user.getUsername()) != null)
					addFieldError("user.username", getText("message.admin.user.username.exist"));
			}
		}
			
		if (StringUtils.isBlank(user.getEmail())) {
			addFieldError("user.email", getText("message.admin.user.email.notempty"));
		} else if (!EmailValidator.getInstance().isValid(user.getEmail())) {
			addFieldError("user.email", getText("message.admin.user.username.notvalid"));
		} else {
			if (StringUtils.isNotBlank(user.getId())) {
				if (!StringUtils.equals(email, user.getEmail())) {
					if (userManager.getUserByEmail(user.getEmail()) != null)
						addFieldError("user.email", getText("message.admin.user.username.exist"));
				}
			} else {
				if (userManager.getUserByEmail(user.getEmail()) != null)
					addFieldError("user.email", getText("message.admin.user.username.exist"));
			}
		}
		
		if(StringUtils.isBlank(user.getPassword()) && StringUtils.isBlank(user.getId())) {
			addFieldError("user.password", getText("message.admin.user.password.notempty"));
		}
		
		if (!StringUtils.equals(user.getPassword(), confirmPassword)) {
			addFieldError("confirmPassword", getText("message.admin.user.password.notmatch"));
		}
	}
}
