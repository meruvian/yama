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
package org.meruvian.yama.security.login.actions;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.security.BackendUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author Dian Aditya
 * 
 */
public class SecurityAction extends DefaultAction {
	private String password = "";

	@Inject
	private BackendUserService userService;

	public String changePassword() {
		if (!password.trim().equalsIgnoreCase("")) {
			User user = (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();

			userService.changePassword(user.getUsername(), password);
		} else {
			return INPUT;
		}

		return SUCCESS;
	}

	public ActionResult changePasswordForm() {
		return new ActionResult(
				"/org/meruvian/yama/view/security/change-password-form.ftl")
				.setType("freemarker");
	}

	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getMethod().equalsIgnoreCase("POST")) {
			return changePassword();
		}

		return INPUT;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
