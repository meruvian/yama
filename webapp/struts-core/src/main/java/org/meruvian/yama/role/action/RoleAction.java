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
package org.meruvian.yama.role.action;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.role.service.RoleService;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/backend/workflow_role")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/workflow_role?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/role/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/role/list.ftl") })
public class RoleAction extends DefaultAction implements
		ModelDriven<RoleActionModel> {
	private RoleActionModel model = new RoleActionModel();

	@Inject
	private RoleService roleService;

	@SkipValidation
	@Action(name = "add", method = HttpMethod.GET)
	public String form() {
		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "role.name", message = "Name cannot be blank", trim = true) })
	public String save() {
		roleService.saveRole(model.getRole());

		return SUCCESS;
	}

	@Action(name = "edit/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setRole(roleService.getRoleById(model.getQ()));

		return INPUT;
	}

	@Action(name = "master/{role.master.id}")
	@SkipValidation
	public String masterList() {
		model.setRoles(roleService.findMastersForRole(model.getRole()
				.getMaster().getId(), model.getQ(), null, "ASC",
				model.getMax(), model.getPage() - 1));

		return INDEX;
	}

	@Action(name = "delete/{role.id}", method = HttpMethod.POST)
	@SkipValidation
	public String delete() {
		roleService.deleteRole(model.getRole());

		return SUCCESS;
	}

	@Action
	@SkipValidation
	public String execute() {
		model.setRoles(roleService.findRoles(model.getQ(), model.getMax(),
				null, "ASC", model.getPage() - 1));

		return INDEX;
	}

	public RoleActionModel getModel() {
		return model;
	}
}
