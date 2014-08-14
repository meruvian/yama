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

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.yama.service.SessionCredential;
import org.meruvian.yama.service.social.SocialManager;
import org.meruvian.yama.service.social.SocialManagerLocator;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/login")
public class LoginAction implements ServletRequestAware {
	@Inject
	private SocialManagerLocator managerLocator;
	
	@Inject
	private SessionCredential sessionCredential;

	private HttpServletRequest request;
	
	@Action
	public ActionResult loginForm() {
		if (sessionCredential.getCurrentUser() != null) {
			return new ActionResult("redirect", getRedirectUrlAfterLogin());
		}
		
		return new ActionResult("freemarker", "/view/login.ftl");
	}
	
	@Action(name = "/social/{social}/auth")
	public ActionResult socialLogin(@ActionParam("social") String social) {
		String redirectUri = managerLocator.getSocialManager(social).getAuthorizeUrl();
		
		return new ActionResult("redirect", redirectUri);
	}
	
	@Action(name = "/social/{social}/callback")
	public ActionResult socialLoginCallback(@ActionParam("social") String social,
			@ActionParam("code") String code) {
		SocialManager<?> manager = managerLocator.getSocialManager(social);
		Connection<?> connection = manager.createConnection(code, null);
		
		if (manager.isAuthorized(connection)) {
			List<String> userIds = manager.getUsersConnectionManager().findUserIdsWithConnection(connection);
			
			if (userIds.size() == 1) { // Signin
				String userId = userIds.get(0);

				sessionCredential.registerAuthentication(userId);
			}
		}
		
		return new ActionResult("redirect", getRedirectUrlAfterLogin());
	}
	
	protected String getRedirectUrlAfterLogin() {
		HttpSession session = request.getSession(false);

		if (session != null) {
			SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			if (savedRequest != null) {
				return savedRequest.getRedirectUrl();
			}
		}

		return "/";
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
