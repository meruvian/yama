package org.meruvian.yama.profile.action;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.profile.service.ProfileService;
import org.meruvian.yama.security.SessionCredentials;
import org.meruvian.yama.security.User;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Action(name = "/module/profile")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/module/profile/view"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/profile/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/profile/view.ftl"),
		@Result(name = "change", type = "freemarker", location = "/view/profile/changepass.ftl") })
public class ProfileAction extends DefaultAction implements
		ModelDriven<ProfileActionModel> {

	private ProfileActionModel model = new ProfileActionModel();

	@Inject
	private ProfileService profileService;

	@Inject
	private PasswordEncoder encoder;

	private EntityListWrapper<User> users = new EntityListWrapper<User>();
	private String birth = "", notifEmail = "", cpass = "";
	DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

	@SkipValidation
	@Action(name = "view", method = HttpMethod.GET)
	public String form() {
		
		
		
		model.setUser(profileService.getUserById(SessionCredentials
				.currentUser().getId()));
		
		return INDEX;
	}

	@SkipValidation
	@Action(name = "edit", method = HttpMethod.GET)
	public String edit() {
		model.setUser(profileService.getUserById(SessionCredentials
				.currentUser().getId()));
		
		return INPUT;
	}

	@Action(name = "save", method = HttpMethod.POST)
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "user.name.first", message = "First Name cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "user.email", message = "Email cannot be blank", trim = true) })
	public String save() {
		try {
			users = profileService.findCategoryByEmail(model.getUser().getId(),
					model.getUser().getEmail(), model.getUser().getUsername(),
					0, 0);
			if (!users.getEntityList().isEmpty()) {
				setNotifEmail("Email is already used");
				return INPUT;
			}

			profileService.save(model.getUser());
		} catch (Exception e) {
			// TODO: handle exception
		}

		return SUCCESS;
	}

	@Action(name = "change", method = HttpMethod.GET)
	@SkipValidation
	public String change() {

		return "change";
	}

	@Action(name = "savepass", method = HttpMethod.POST)
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "user.password", message = "Password cannot be blank", trim = true) })
	public String savepass() {

		if ((!getCpass().equalsIgnoreCase(model.getUser().getPassword()))
				|| (getCpass().equalsIgnoreCase(""))) {
			return "change";
		}
		if (!StringUtils.isEmpty(model.getUser().getPassword())) {
			model.getUser()
					.setPassword(
							encoder.encodePassword(model.getUser()
									.getPassword(), null));
		}
		profileService.savePass(model.getUser());

		return SUCCESS;
	}

	public ProfileActionModel getModel() {
		return model;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getNotifEmail() {
		return notifEmail;
	}

	public void setNotifEmail(String notifEmail) {
		this.notifEmail = notifEmail;
	}

	public String getCpass() {
		return cpass;
	}

	public void setCpass(String cpass) {
		this.cpass = cpass;
	}

}
