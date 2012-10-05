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
package org.meruvian.yama.roleprivilege.action;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.modulefunction.service.ModuleFunctionService;
import org.meruvian.yama.role.service.RoleService;
import org.meruvian.yama.roleprivilege.RolePrivilege;
import org.meruvian.yama.roleprivilege.service.RolePrivilegeService;

import com.opensymphony.xwork2.ModelDriven;

@Action(name = "/backend/role_privilege")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/role_privilege/edit/${role.id}?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/role/role_privilege.ftl"),
		@Result(name = "redirect", type = "redirect", location = "/backend/workflow_role") })
public class RolePrivilegeAction extends DefaultAction implements
		ModelDriven<RolePrivilegeActionModel> {
	@Inject
	private RoleService roleService;

	@Inject
	private RolePrivilegeService rolePrivilegeService;

	@Inject
	private ModuleFunctionService moduleFunctionService;

	private RolePrivilegeActionModel model = new RolePrivilegeActionModel();

	@Action(name = "edit/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setRole(roleService.getRoleById(model.getQ()));
		model.setModuleFunctions(rolePrivilegeService.findModuleFunctionByRole(
				model.getQ(), model.getOrderBy(), "ASC", model.getMax(),
				model.getPage() - 1));
		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	public String save() {
		if (!(StringUtils.isBlank(model.getModuleFunction().getId()) || StringUtils
				.isBlank(model.getRole().getId()))) {
			RolePrivilege rolePrivilege = new RolePrivilege();
			rolePrivilege.setRole(model.getRole());
			rolePrivilege.setModuleFunction(moduleFunctionService
					.getModuleFunctionById(model.getModuleFunction().getId()));
			rolePrivilegeService.save(rolePrivilege);
		}

		return SUCCESS;
	}

	@Action(name = "mf/delete/{role.id}/{moduleFunction.id}")
	@SkipValidation
	public String removeModuleFunction() {
		rolePrivilegeService.delete(model.getRole().getId(), model
				.getModuleFunction().getId());

		return SUCCESS;
	}

	@Action(name = "mf/list/{role.id}")
	@SkipValidation
	public ActionResult findModuleFunction() {
		model.setModuleFunctions(rolePrivilegeService
				.findModuleFunctionByRolePrivilege(model.getRole().getId(),
						model.getQ(), model.getOrderBy(), model.getOrder(),
						model.getMax(), model.getPage() - 1));

		return new ActionResult("/view/modulefunction/list.ftl")
				.setType("freemarker");
	}

	@SkipValidation
	public String execute() throws Exception {
		return "redirect";
	}

	@Override
	public RolePrivilegeActionModel getModel() {
		return model;
	}
}
