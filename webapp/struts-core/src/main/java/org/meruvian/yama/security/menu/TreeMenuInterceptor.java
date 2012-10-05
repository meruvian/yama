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
package org.meruvian.yama.security.menu;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.role.Role;
import org.meruvian.yama.roleprivilege.service.RolePrivilegeService;
import org.meruvian.yama.security.SessionCredentials;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Dian Aditya
 * 
 */
public class TreeMenuInterceptor extends AbstractInterceptor implements
		StrutsStatics {
	@Inject
	private RolePrivilegeService privilegeService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(HTTP_REQUEST);
		if ((request.getContextPath() + request.getServletPath())
				.equalsIgnoreCase("/")) {
			return invocation.invoke();
		}

		Role role = SessionCredentials.currentRole();
		// if (role == null)
		// return Action.LOGIN;

		if (role != null) {
			EntityListWrapper<ModuleFunction> moduleFunctions = privilegeService
					.findModuleFunctionByRole(role.getId(), null, "ASC", 0, 1);

			invocation.getStack().set("menus", moduleFunctions);
		}

		return invocation.invoke();
	}
}
