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

import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;

/**
 * @author Dian Aditya
 *
 */
@Action
public class IndexAction {
	@Action
	public ActionResult index() {
		return new ActionResult("freemarker", "/view/dashboard.ftl");
	}
	
	@Action(name = "/login")
	public ActionResult loginForm() {
		return new ActionResult("freemarker", "/view/login.ftl");
	}
	
	@Action(name = "/register", method = HttpMethod.GET)
	public ActionResult registerForm() {
		return new ActionResult("freemarker", "/view/regsiter.ftl");
	}
	
	@Action(name = "/register", method = HttpMethod.POST)
	public ActionResult register() {
		return new ActionResult("freemarker", "/view/regsiter-success.ftl");
	}
}
