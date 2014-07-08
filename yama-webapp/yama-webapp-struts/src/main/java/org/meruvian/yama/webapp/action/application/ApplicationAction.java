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
package org.meruvian.yama.webapp.action.application;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.yama.repository.application.Application;
import org.meruvian.yama.repository.application.DefaultApplication;
import org.meruvian.yama.service.ApplicationManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/applications")
public class ApplicationAction extends ActionSupport {
	@Inject
	private ApplicationManager appManager;
	
	@Action(method = HttpMethod.GET)
	public ActionResult appList(@ActionParam("q") String q, @ActionParam("max") int max, @ActionParam("page") int page) {
		max = max == 0 ? 10 : max;
		q = StringUtils.defaultIfBlank(q, "");
		
		Page<? extends Application> apps = appManager.findApplicationByNamespace(q, new PageRequest(page, max));
		
		return new ActionResult("freemarker","/view/application/application-list.ftl").addToModel("apps", apps);
	}
	
	@Action(name = "/{name}/edit", method = HttpMethod.GET)
	public ActionResult appForm(@ActionParam("name") String name) {
		ActionResult actionResult = new ActionResult("freemarker", "/view/application/application-form.ftl");
		
		if (!StringUtils.equalsIgnoreCase(name, "-")) {
			Application app = appManager.getApplicationById(name);
			actionResult.addToModel("app", app);
		} else {
			actionResult.addToModel("app", new DefaultApplication());
		}
		
		return actionResult;
	}
	
	@Action(name = "/{appname}/edit", method = HttpMethod.POST)
	public ActionResult updateApplication(@ActionParam("appname") String name,  @ActionParam("app") DefaultApplication app) {
		validateApplication(app, name);
		
		if (hasFieldErrors()) {
			return new ActionResult("freemarker", "/view/application/application-form.ftl");
		}
		
		Application r = appManager.saveApplication(app);
		String redirectUri = "/applications/" + r.getId() + "/edit?success";
		
		return new ActionResult("redirect", redirectUri);
	}
	
	@Action(name = "/{appname}/secret", method = HttpMethod.POST)
	public ActionResult generateNewSecret(@ActionParam("appname") final String name) {
		appManager.generateNewSecret(new DefaultApplication() {{ setId(name); }});
		
		return new ActionResult("redirect", "/applications/" + name + "/edit");
	}
	
	private void validateApplication(Application app, String appname) {
		if (StringUtils.isBlank(app.getDisplayName())) {
			addFieldError("app.displayName", getText("message.application.name.notempty"));
		}
		
		if (StringUtils.isBlank(app.getSite())) {
			addFieldError("app.site", getText("message.application.site.notempty"));
		}
		
		UrlValidator validator = new UrlValidator(new String[]{ "http", "https" });
		if (!validator.isValid(app.getSite())) {
			addFieldError("app.site", getText("message.application.site.notvalid"));
		}
	}
}
