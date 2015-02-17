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
package org.meruvian.yama.webapi.service;

import java.io.IOException;

import javax.inject.Inject;

import org.meruvian.yama.core.commons.FileInfo;
import org.meruvian.yama.core.role.Role;
import org.meruvian.yama.core.role.RoleRepository;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.social.core.SocialService;
import org.meruvian.yama.social.core.SocialServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 *
 */
@Service
@Transactional
public class RestSignUpService implements SignUpService, ConnectionSignUp, EnvironmentAware {
	private static final Logger LOG = LoggerFactory.getLogger(RestSignUpService.class);
	
	@Inject
	private SocialServiceLocator socialServiceLocator;
	
	@Inject
	private UserService userService;
	
	@Inject
	private RoleRepository roleRepository;
	
	private String defaultRole;
	
	@Override
	@Transactional
	public User signUp(User user) {
		return userService.saveUser(user);
	}

	@Override
	@Transactional
	public String execute(Connection<?> connection) {
		SocialService<?> socialService = socialServiceLocator.getSocialService(connection.getKey().getProviderId());
		User createdUser = socialService.createUser(connection);
		User user = userService.getUserByUsernameOrId(createdUser.getEmail());
		FileInfo fileInfo = createdUser.getFileInfo();
		
		if (user != null) {
			createdUser = user;
		} else {
			createdUser.setFileInfo(null);
			createdUser = userService.saveUser(createdUser);
			
			Role role = roleRepository.findByName(defaultRole);
			if (role != null) {
				userService.addRoleToUser(createdUser.getId(), role.getId());
			}
		}
		
		if (fileInfo != null) {
			try {
				userService.updateUserPhoto(createdUser.getId(), fileInfo.getDataBlob());
			} catch (IOException e) {
				LOG.error(e.getMessage(), e);
			}
		}
		
		return createdUser.getId();
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.defaultRole = environment.getProperty("default.role");
	}
}
