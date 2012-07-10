/**
 * Copyright 2012 BlueOxygen Technology
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

import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.security.BackendUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Dian Aditya
 * 
 */
public class LoginAction extends DefaultAction {
	@Inject
	private BackendUserService userService;

	@Value("${init.user}")
	private boolean init;

	@Override
	public String execute() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Object principal = authentication.getPrincipal();

		if (init) {
			if (userService.rowCount() < 1) {
				userService.initUser();
			}
		}

		if (principal instanceof String
				&& principal.toString().equals("anonymousUser")) {
			return INDEX;
		} else {
			return "home";
		}
	}
}
