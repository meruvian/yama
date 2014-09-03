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

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.yama.core.user.DefaultUser;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserManager;
import org.meruvian.yama.web.CredentialsManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/register")
public class RegistrationAction extends ActionSupport implements ServletRequestAware {
	@Inject
	private UserManager userManager;
	
	@Inject
	private ReCaptcha reCaptcha;
	
	@Inject
	private CredentialsManager credentialsManager;
	
	private boolean recaptchaActive;
	private String recaptchaPublicKey;

	private HttpServletRequest request;
	
	@Inject
	@Named("yamaProperties")
	public void setProperties(Properties properties) {
		this.recaptchaActive = Boolean.parseBoolean(properties.getProperty("recaptcha.active"));
		this.recaptchaPublicKey = properties.getProperty("recaptcha.public.key");
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	@Action(method = HttpMethod.GET)
	public ActionResult registrationForm() {
		return new ActionResult("freemarker", "/view/register.ftl")
			.addToModel("reCaptchaActive", recaptchaActive)
			.addToModel("reCaptcha", reCaptcha)
			.addToModel("reCaptchaPublicKey", recaptchaPublicKey);
	}
	
	@Action(method = HttpMethod.POST)
	public ActionResult register(@ActionParam("user") DefaultUser user,
			@ActionParam("confirmPassword") String confirmPassword,
			@ActionParam("recaptcha_challenge_field") String captchaChallenge,
			@ActionParam("recaptcha_response_field") String captchaResponse) {
		validate(user, confirmPassword, captchaChallenge, captchaResponse);
		
		if (hasFieldErrors()) {
			return new ActionResult("freemarker", "/view/register.ftl")
				.addToModel("reCaptchaActive", recaptchaActive)
				.addToModel("reCaptcha", reCaptcha)
				.addToModel("reCaptchaPublicKey", recaptchaPublicKey);
		}
		
		User u = userManager.saveUser(user);
		credentialsManager.registerAuthentication(u.getId());
		
		return new ActionResult("redirect", "/profile?registrationSuccess");
	}
	
	private void validate(DefaultUser user, String confirmPassword,
			String captchaChallenge, String captchaResponse) {
		if (StringUtils.isBlank(user.getUsername()))
			addFieldError("user.username", getText("message.register.username.notempty"));
		else if (userManager.getUserByUsername(user.getUsername()) != null)
			addFieldError("user.username", getText("message.register.username.exist"));
		if (StringUtils.isBlank(user.getEmail()))
			addFieldError("user.email", getText("message.register.email.notempty"));
		else if (!EmailValidator.getInstance().isValid(user.getEmail()))
			addFieldError("user.email", getText("message.register.email.notvalid"));
		if (userManager.getUserByEmail(user.getEmail()) != null)
			addFieldError("user.email", getText("message.register.email.exist"));
		if (StringUtils.isBlank(user.getPassword()))
			addFieldError("user.password", getText("message.register.password.notempty"));
		if (!StringUtils.equals(user.getPassword(), confirmPassword))
			addFieldError("confirmPassword", getText("message.register.password.notmatch"));
		
		if (recaptchaActive) {
			ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(request.getRemoteAddr(),
					captchaChallenge, captchaResponse);
			if (!reCaptchaResponse.isValid())
				addFieldError("user.reCaptcha", getText("message.register.captcha.wronganswer"));
		}
	}
}
