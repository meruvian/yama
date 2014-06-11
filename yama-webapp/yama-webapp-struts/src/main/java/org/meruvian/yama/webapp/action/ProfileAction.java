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
package org.meruvian.yama.webapp.action;

import javax.inject.Inject;

import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.yama.repository.jpa.user.JpaUser;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.SessionCredential;
import org.meruvian.yama.service.UserManager;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/profile")
public class ProfileAction {
	@Inject
	private UserManager userManager;
	
	@Inject
	private SessionCredential sessionCredential;
	
	private User user = new JpaUser();

	@Action(method = HttpMethod.GET)
	public ActionResult profileForm() {
		user = sessionCredential.getCurrentUser();
		
		return new ActionResult("freemarker", "/view/profile/profile-form.ftl");
	}
	
	@Action(method = HttpMethod.POST)
	public ActionResult profile() {
		userManager.saveUser(user);
		
		return new ActionResult("redirect", "/profile?success");
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(JpaUser user) {
		this.user = user;
	}
}
