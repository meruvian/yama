package org.meruvian.yama.security;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@Action(name = "/security/password")
@Results({
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/cimande/resetpasswordform.ftl"),
		@Result(name = DefaultAction.SUCCESS, type = "freemarker", location = "/view/cimande/information.ftl") })
public class ResetPasswordAction extends DefaultAction {

	private static final long serialVersionUID = -5158467193444905535L;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder encoder;

	private String confirmationId;
	private String password = "";
	private String cpassword = "";
	private String msg;

	@SkipValidation
	@Action(name = "reset", method = HttpMethod.GET)
	public String resetForm() {
		return INPUT;
	}

	@Action(name = "reset", method = HttpMethod.POST)
	public String resetPost() {
		User user = userService.getUserById(confirmationId);
		user.setPassword(encoder.encodePassword(getPassword(), null));
		userService.saveUser(user);
		setMsg("Reset password success");
		return SUCCESS;
	}

	@Override
	public void validate() {
		boolean fail = false;
		if (getPassword().isEmpty()) {
			addFieldError("password", "Please input a new password");
			fail = true;
		}
		if (getCpassword().isEmpty()) {
			addFieldError("cpassword", "Please input a confirm new password");
			fail = true;
		}
		if (!fail && !getPassword().equalsIgnoreCase(getCpassword())) {
			addFieldError("cpassword", "Confirm password doesn't match");
		}
	}

	public String getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
