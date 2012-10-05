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
package org.meruvian.yama.userprivilege.action;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.role.service.RoleService;
import org.meruvian.yama.user.service.UserService;
import org.meruvian.yama.userprivilege.UserPrivilege;
import org.meruvian.yama.userprivilege.service.UserPrivilegeService;

import com.opensymphony.xwork2.ModelDriven;

@Action(name = "/backend/user_privilege")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/user_privilege/edit/${user.id}?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/user/user_privilege.ftl"),
		@Result(name = "redirect", type = "redirect", location = "/backend/user") })
public class UserPrivilegeAction extends DefaultAction implements
		ModelDriven<UserPrivilegeActionModel> {
	@Inject
	private RoleService roleService;

	@Inject
	private UserService userService;

	@Inject
	private UserPrivilegeService userPrivilegeService;

	private UserPrivilegeActionModel model = new UserPrivilegeActionModel();

	@Action(name = "edit/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setUser(userService.getUserById(model.getQ()));
		model.setRoles(userPrivilegeService.findRoleByUser(model.getQ(),
				model.getOrderBy(), "ASC", model.getMax(), model.getPage() - 1));

		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	public String save() {
		if (!(StringUtils.isBlank(model.getUser().getId()) || StringUtils
				.isBlank(model.getRole().getId()))) {
			UserPrivilege userPrivilege = new UserPrivilege();
			userPrivilege.setUser(model.getUser());
			userPrivilege.setRole(roleService.getRoleById(model.getRole()
					.getId()));

			userPrivilegeService.save(userPrivilege);
		}

		return SUCCESS;
	}

	@Action(name = "role/delete/{user.id}/{role.id}", method = HttpMethod.POST)
	@SkipValidation
	public String removeRole() {
		userPrivilegeService.delete(model.getUser().getId(), model.getRole()
				.getId());

		return SUCCESS;
	}

	@Action(name = "role/list/{user.id}")
	@SkipValidation
	public ActionResult findModuleFunction() {
		model.setRoles(userPrivilegeService.findRoleByUserPrivilege(model
				.getUser().getId(), model.getQ(), model.getOrderBy(), model
				.getOrder(), model.getMax(), model.getPage() - 1));

		return new ActionResult("/view/role/list.ftl").setType("freemarker");
	}

	@SkipValidation
	public String execute() throws Exception {
		return "redirect";
	}

	@Override
	public UserPrivilegeActionModel getModel() {
		return model;
	}
}
