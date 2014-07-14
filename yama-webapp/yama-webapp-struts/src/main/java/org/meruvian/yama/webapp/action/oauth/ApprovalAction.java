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
package org.meruvian.yama.webapp.action.oauth;

import javax.inject.Inject;

import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.yama.repository.application.Application;
import org.meruvian.yama.service.ApplicationManager;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/oauth")
public class ApprovalAction {
	@Inject
	private ApplicationManager applicationManager;
	
	@Action(name = "/approval")
	public ActionResult approvalForm(@ActionParam("client_id") String clientId) {
		Application application = applicationManager.getApplicationById(clientId);
		
		return new ActionResult("freemarker", "/view/oauth/approval.ftl").addToModel("app", application);
	}
}
