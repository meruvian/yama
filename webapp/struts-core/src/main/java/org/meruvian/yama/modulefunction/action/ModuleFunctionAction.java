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
package org.meruvian.yama.modulefunction.action;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.modulefunction.service.ModuleFunctionService;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Dian Aditya
 * 
 */
@Action(name = "/backend/module_function")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/module_function?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/modulefunction/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/modulefunction/list.ftl") })
public class ModuleFunctionAction extends DefaultAction implements
		ModelDriven<ModuleFunctionActionModel> {
	private ModuleFunctionActionModel model = new ModuleFunctionActionModel();

	@Inject
	private ModuleFunctionService moduleFunctionService;

	@SkipValidation
	@Action(name = "add", method = HttpMethod.GET)
	public String form() {
		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "moduleFunction.name", message = "Name cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "moduleFunction.moduleUrl", message = "Module URL cannot be blank", trim = true) })
	public String save() {
		moduleFunctionService.saveModuleFunction(model.getModuleFunction());

		return SUCCESS;
	}

	@Action(name = "edit/{moduleFunction.master.id}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		String masterId = model.getModuleFunction().getMaster().getId();

		model.setModuleFunction(moduleFunctionService
				.getModuleFunctionById(masterId));

		return INPUT;
	}

	@Action(name = "master/{moduleFunction.master.id}")
	@SkipValidation
	public String masterList() {
		model.setModuleFunctions(moduleFunctionService.findMasters(model
				.getModuleFunction().getMaster().getId(), model.getQ(),
				model.getMax(), null, "ASC", model.getPage() - 1));

		return INDEX;
	}

	@Action(name = "delete/{moduleFunction.id}", method = HttpMethod.POST)
	@SkipValidation
	public String delete() {
		moduleFunctionService.deleteModuleFunction(model.getModuleFunction());

		return SUCCESS;
	}

	@Action
	@SkipValidation
	public String execute() {
		model.setModuleFunctions(moduleFunctionService.findModuleFunctions(
				model.getQ(), model.getMax(), null, "ASC", model.getPage() - 1));

		return INDEX;
	}

	public ModuleFunctionActionModel getModel() {
		return model;
	}
}
