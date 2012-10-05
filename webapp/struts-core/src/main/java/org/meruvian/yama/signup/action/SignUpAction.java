package org.meruvian.yama.signup.action;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.security.User;
import org.meruvian.yama.signup.service.SignUpService;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Action(name = "/user")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/user/signup/success?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/signup/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/signup/success.ftl") })
public class SignUpAction extends DefaultAction implements
		ModelDriven<SignUpActionModel> {
	private SignUpActionModel model = new SignUpActionModel();

	@Inject
	private SignUpService signUpService;

	@Inject
	private ReCaptcha reCaptcha;

	@Inject
	private PasswordEncoder encoder;
	private String cpass = "", agree = "", notifCaptcha = "", notifTerm = "",
			notifEmail = "";
	private EntityListWrapper<User> users = new EntityListWrapper<User>();

	@SkipValidation
	@Action(name = "signup", method = HttpMethod.GET)
	public String form() {

		return INPUT;
	}

	@Action(name = "signup", method = HttpMethod.POST)
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "signUp.username", message = "Usermame cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "signUp.password", message = "Password cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "signUp.name.first", message = "First Name cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "signUp.name.last", message = " Last Name be blank", trim = true),
			@RequiredStringValidator(fieldName = "signUp.email", message = "Email cannot be blank", trim = true) })
	public String save() {

		HttpServletRequest request = ServletActionContext.getRequest();
		String remoteAddr = request.getRemoteAddr();

		if ((!getCpass().equalsIgnoreCase(model.getSignUp().getPassword()))
				|| (getCpass().equalsIgnoreCase(""))) {
			return INPUT;
		}

		users = signUpService.findCategoryByEmail(model.getSignUp().getEmail(),
				model.getSignUp().getUsername(), 0, 0);
		if (!users.getEntityList().isEmpty()) {
			setNotifEmail("Username or email is already used");
			return INPUT;
		}

		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr,
				challenge, uresponse);

		if (!reCaptchaResponse.isValid()) {
			setNotifCaptcha("The characters you entered didn't match the word verification. Please try again.");
			return INPUT;
		}

		
		model.getSignUp().setPassword(
				encoder.encodePassword(model.getSignUp().getPassword(), null));

		signUpService.signUp(model.getSignUp());

		return SUCCESS;
	}

	@Action(name = "signup/success")
	@SkipValidation
	public String execute() {

		return INDEX;
	}

	public SignUpActionModel getModel() {
		return model;
	}

	public String getCpass() {
		return cpass;
	}

	public void setCpass(String cpass) {
		this.cpass = cpass;
	}

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getNotifCaptcha() {
		return notifCaptcha;
	}

	public void setNotifCaptcha(String notifCaptcha) {
		this.notifCaptcha = notifCaptcha;
	}

	public String getNotifTerm() {
		return notifTerm;
	}

	public void setNotifTerm(String notifTerm) {
		this.notifTerm = notifTerm;
	}

	public String getNotifEmail() {
		return notifEmail;
	}

	public void setNotifEmail(String notifEmail) {
		this.notifEmail = notifEmail;
	}

}
