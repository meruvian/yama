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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.meruvian.inca.struts2.rest.ActionResult;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.ActionParam;
import org.meruvian.inca.struts2.rest.annotation.Param;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.repository.commons.DefaultFileInfo;
import org.meruvian.yama.repository.commons.FileInfo;
import org.meruvian.yama.repository.user.DefaultUser;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.FileInfoManager;
import org.meruvian.yama.service.SessionCredential;
import org.meruvian.yama.service.UserManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Dian Aditya
 *
 */
@Action(name = "/profile")
public class ProfileAction extends ActionSupport {
	@Inject
	private UserManager userManager;
	
	@Inject
	private FileInfoManager fileInfoManager;
	
	@Inject
	private SessionCredential sessionCredential;
	
	private InputStream picture;

	public InputStream getPicture() {
		return picture;
	}
	
	@Action(method = HttpMethod.GET)
	public ActionResult profileForm() {
		User user = sessionCredential.getCurrentUser();
		
		return new ActionResult("freemarker", "/view/profile/profile-form.ftl").addToModel("user", user);
	}
	
	@Action(method = HttpMethod.POST)
	public ActionResult profile(@ActionParam("user") DefaultUser user, @ActionParam("edit") String edit, 
			@ActionParam("confirmPassword") String copass, @ActionParam("profilePicture") File file,
			@ActionParam("profilePictureFileName") String fileName) throws IOException {
		User u = sessionCredential.getCurrentUser();
		
		if (StringUtils.equalsIgnoreCase(edit, "password")) {
			validatePassword(user.getPassword(), copass);
			if (hasFieldErrors())
				return new ActionResult("freemarker", "/view/profile/profile-form.ftl");
			
			userManager.updateUserPassword(u, null, user.getPassword());
		} else if (StringUtils.equalsIgnoreCase(edit, "photo")) {
			validateImage(file);
			if (hasFieldErrors())
				return new ActionResult("freemarker", "/view/profile/profile-form.ftl");
			
			DefaultFileInfo fileInfo = new DefaultFileInfo();
			fileInfo.setOriginalName(fileName);
			fileInfo.setDataBlob(new FileInputStream(file));
			fileInfo.setPath("");
			
			FileInfo f = fileInfoManager.saveFileInfo(fileInfo);
			userManager.setUserProfilePicture(u, f);
		} else {
			validateUser(user);
			if (hasFieldErrors())
				return new ActionResult("freemarker", "/view/profile/profile-form.ftl");
			
			userManager.saveUser(user);
		}
		
		return new ActionResult("redirect", "/profile?success");
	}

	@Action(name = "/photo")
	@Results({
		@Result(name = "no-profile", type = "redirect", location = "/images/no-profile.jpg"),
		@Result(name = "photo", type = "stream", 
			params = { 
				@Param(name = "contentType", value = "image/jpg"),
				@Param(name = "inputName", value = "picture")
			})
	})
	public String getPhoto() throws IOException {
		User u = sessionCredential.getCurrentUser();
		FileInfo fileInfo = u.getFileInfo();
		if (fileInfo == null) {
			return "no-profile";
		}
		
		fileInfo = fileInfoManager.getFileInfoById(fileInfo.getId());
		picture = fileInfo.getDataBlob();
		
		return "photo";
	}
	
	private void validateImage(File file) {
		if (file != null) {
			try {
				BufferedImage image = ImageIO.read(file);
				if (image != null) return;
			} catch (IOException e) {
			}
		}
		
		addFieldError("profilePicture", getText("message.profile.picture.notvalid"));
	}

	private void validatePassword(String password, String copass) {
		if (!StringUtils.equals(password, copass))
			addFieldError("confirmPassword", getText("message.profile.password.notmatch"));
		if (StringUtils.isBlank(password))
			addFieldError("user.password", getText("message.profile.password.notempty"));
	}

	private void validateUser(DefaultUser user) {
		User currentUser = sessionCredential.getCurrentUser();
		
		if (StringUtils.isBlank(user.getUsername()))
			addFieldError("user.username", getText("message.profile.username.notempty"));
		else {
			String username = currentUser.getUsername();
			
			if (!StringUtils.equals(username, user.getUsername()))
				if (userManager.getUserByUsername(user.getUsername()) != null)
					addFieldError("user.username", getText("message.profile.username.exist"));
		}
		if (StringUtils.isBlank(user.getEmail()))
			addFieldError("user.email", getText("message.profile.email.notempty"));
		else if (!EmailValidator.getInstance().isValid(user.getEmail()))
			addFieldError("user.email", getText("message.profile.email.notvalid"));
		else {
			String email = currentUser.getEmail();
			
			if (!StringUtils.equals(email, user.getEmail())) {
				if (userManager.getUserByEmail(user.getEmail()) != null)
					addFieldError("user.email", getText("message.profile.email.exist"));
			}
		}
	}
}
