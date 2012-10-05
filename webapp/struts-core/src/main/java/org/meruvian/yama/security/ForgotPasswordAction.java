package org.meruvian.yama.security;

import javax.persistence.NoResultException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Action(name = "/security/password")
@Results({
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/cimande/fpassword.ftl"),
		@Result(name = DefaultAction.SUCCESS, type = "freemarker", location = "/view/cimande/information.ftl"),
		@Result(name = DefaultAction.EDIT, type = "freemarker", location = "/view/cimande/resetpasswordform.ftl") })
public class ForgotPasswordAction extends DefaultAction {

	private static final long serialVersionUID = -6948488839511514898L;

	@Autowired
	private UserService userService;

	private String email;
	private String msg;

	@SkipValidation
	@Action(name = "forgot", method = HttpMethod.GET)
	public String form() {
		return INPUT;
	}

	@Action(name = "forgot", method = HttpMethod.POST)
	public String forgot() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println("   " + threadName + " beginning work ");
				User user = userService.getUserByEmail(getEmail());
				emailNotification(
						getEmail(),
						"Please follow this link to reset password: "
								+ user.getId());
				System.out.println("   " + threadName + " complete ");
			}
		}).start();
		setMsg("<h4>Confirmation</h4><br/>Request password success, please check your email");
		return SUCCESS;
	}

	@Override
	public void validate() {
		if (getEmail().isEmpty()) {
			addFieldError("email", "Please enter your email address");
		} else {
			try {
				userService.getUserByEmail(getEmail());
			} catch (NoResultException e) {
				addFieldError("email",
						"We don't have user registered with the given email address");
			}
		}
	}

	public void emailNotification(String toAddress, String content) {
		HtmlEmail mail = new HtmlEmail();
		mail.setHostName("smtp.gmail.com");
		mail.setSmtpPort(465);
		mail.setAuthentication("course@meruvian.org", "tulalit13");
		mail.setSSL(true);
		try {
			mail.addTo(toAddress);
			mail.setFrom("course@meruvian.org", "Meruvian Yama");
			mail.setSubject("Yama : Email Notification");
			mail.setHtmlMsg(content);
			mail.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
