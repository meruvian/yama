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
package org.meruvian.yama.user.action;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.user.service.UserService;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Edy Setiawan
 * 
 */

@Action(name = "/backend/user")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/user?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/user/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/user/list.ftl") })
public class UserAction extends DefaultAction implements
		ModelDriven<UserActionModel> {

	private UserActionModel model = new UserActionModel();

	@Inject
	private UserService userService;

	@Inject
	private PasswordEncoder encoder;

	@SkipValidation
	@Action(name = "add", method = HttpMethod.GET)
	public String form() {

		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "user.username", message = "Usermame cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "user.password", message = "Password cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "user.name.first", message = "First Name cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "user.name.last", message = " Last Name be blank", trim = true) })
	public String save() {

		model.getUser().setPassword(
				encoder.encodePassword(model.getUser().getPassword(), null));
	

		userService.saveUser(model.getUser());

		return SUCCESS;
	}

	@Action
	@SkipValidation
	public String execute() {
		model.setUsers(userService.findUsers(model.getQ(), model.getMax(),
				null, "ASC", model.getPage() - 1));
		return INDEX;
	}

	@Action(name = "edit/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setUser(userService.getUserById(model.getQ()));

		return INPUT;
	}

	@Action(name = "delete/{user.id}", method = HttpMethod.POST)
	@SkipValidation
	public String delete() {
		userService.deleteUser(model.getUser());

		return SUCCESS;
	}

	public UserActionModel getModel() {
		return model;
	}

}
