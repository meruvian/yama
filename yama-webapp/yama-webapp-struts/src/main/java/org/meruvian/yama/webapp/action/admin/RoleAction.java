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
import org.meruvian.yama.repository.role.DefaultRole;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.service.RoleManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/admin/roles")
public class RoleAction extends ActionSupport {
	@Inject
	private RoleManager roleManager;
	
	@Action(method = HttpMethod.GET)
	public ActionResult roleList(@ActionParam("q") String q, @ActionParam("max") int max,
			@ActionParam("page") int page) {
		max = max == 0 ? 10 : max;
		Page<? extends Role> roles = roleManager.findRoleByKeyword(q, new PageRequest(page, max));
		
		return new ActionResult("freemarker","/view/admin/role/role-list.ftl")
				.addToModel("roles", roles);
	}
	
	@Action(name = "/{name}/edit", method = HttpMethod.GET)
	public ActionResult roleForm(@ActionParam("name") String name) {
		ActionResult actionResult = new ActionResult("freemarker", "/view/admin/role/role-form.ftl");
		
		if (!StringUtils.equalsIgnoreCase(name, "-")) {
			Role role = roleManager.getRoleByName(name);
			actionResult.addToModel("role", role);
		} else {
			actionResult.addToModel("role", new DefaultRole());
		}
		
		return actionResult;
	}
	
	@Action(name = "/{rolename}/edit", method = HttpMethod.POST)
	public ActionResult updateRole(@ActionParam("rolename") String name, 
			@ActionParam("role") DefaultRole role, @ActionParam("roles") String[] roles) {
		validateRole(role, name);
		if (hasFieldErrors()) {
			return new ActionResult("freemarker", "/view/admin/role/role-form.ftl");
		}
		
		Role r = roleManager.saveRole(role);
		String redirectUri = "/admin/roles/" + r.getName() + "/edit?success";
		
		if (StringUtils.equalsIgnoreCase(name, "-")) {
			redirectUri = "/admin/roles?success";
		}
		
		return new ActionResult("redirect", redirectUri);
	}
	
	private void validateRole(Role role, String rolename) {
		if (StringUtils.isBlank(role.getName())) {
			addFieldError("role.name", getText("message.admin.role.name.notempty"));
		} else if (!StringUtils.equalsIgnoreCase(role.getName(), rolename)) {
			if (roleManager.getRoleByName(role.getName()) != null) {
				addFieldError("role.name", getText("message.admin.role.name.exist"));
			}
		}
	}
}
