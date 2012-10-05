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

import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;

/**
 * @author Dian Aditya
 * 
 */
// @Action(name = "/backend/role_privilege")
// @Results({
// @Result(name = DefaultAction.SUCCESS, type = "redirect", location =
// "/backend/role_privilege?success"),
// @Result(name = DefaultAction.INDEX, type = "freemarker", location =
// "/view/role_privilege/list.ftl") })
public class RoleSiteAction extends RoleAction {
	@Action(name = "edit/{role.id}", method = HttpMethod.GET)
	public String editRole() {

		return SUCCESS;
	}
}
