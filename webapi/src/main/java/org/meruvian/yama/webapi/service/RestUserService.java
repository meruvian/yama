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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.core.LogInformation;
import org.meruvian.yama.core.commons.FileInfo;
import org.meruvian.yama.core.role.Role;
import org.meruvian.yama.core.role.UserRole;
import org.meruvian.yama.core.role.UserRoleRepository;
import org.meruvian.yama.core.user.User;
import org.meruvian.yama.core.user.UserRepository;
import org.meruvian.yama.webapi.service.commons.FileInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 *
 */
@Service
@Transactional(readOnly = true)
public class RestUserService implements UserService, EnvironmentAware {
	private static final Logger LOG = LoggerFactory.getLogger(RestUserService.class);
	
	@Inject
	private UserRepository userRepository;
	
	@Inject
	private UserRoleRepository userRoleRepository;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Inject
	private FileInfoService fileInfoService;
	
	@Inject
	private ApplicationContext context;
	
	private String uploadPath;
	private String profilePicPath;
	private String profilePicContentType;
	
	@Context
	private HttpServletRequest request;
	
	@Override
	public void setEnvironment(Environment environment) {
		uploadPath = environment.getProperty("upload.path.profile_pic");
		profilePicPath = environment.getProperty("default.profile_pic.path");
		profilePicContentType = environment.getProperty("default.profile_pic.content_type");
	}
	
	@Override
	public User getUserByUsernameOrId(String username) {
		User u = userRepository.findById(username);
		u = (u == null) ? userRepository.findByUsername(username) : u;
		u = (u == null) ? userRepository.findByEmail(username) : u;
		
		return u;
	}

	@Override
	public Page<User> findUserByKeyword(String keyword, Pageable pageable) {
		return userRepository.findByUsername(keyword, LogInformation.ACTIVE, pageable);
	}

	@Override
	@Transactional
	public void removeUser(String username) {
		getUserByUsernameOrId(username).getLogInformation().setActiveFlag(LogInformation.INACTIVE);
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} else {
			user.setPassword(passwordEncoder.encode(RandomStringUtils.random(9)));
		}
		
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User updateUser(String username, User user) {
		User u = getUserByUsernameOrId(username);
		u.setUsername(user.getUsername());
		u.setName(user.getName());
		u.setEmail(user.getEmail());
		u.setAddress(user.getAddress());
		
		return u;
	}
	
	@Override
	@Transactional
	public User updateUserPassword(String username, User user) {
		User u = getUserByUsernameOrId(username);
		u.setPassword(passwordEncoder.encode(user.getPassword()));

		return u;
	}

	@Override
	@Transactional
	public boolean addRoleToUser(String username, String roleId) {
		User u = getUserByUsernameOrId(username);
		for (UserRole ur : u.getRoles()) {
			if (ur.getRole().getId().equals(roleId)) {
				return false;
			}
		}
		
		UserRole userRole = new UserRole();
		userRole.setUser(u);
		Role role = new Role();
		role.setId(roleId);
		userRole.setRole(role);
		
		userRoleRepository.save(userRole);
		
		return true;
	}

	@Override
	@Transactional
	public boolean removeRoleFromUser(String username, String roleId) {
		User u = getUserByUsernameOrId(username);
		UserRole ur = userRoleRepository.findByUserIdAndRoleId(u.getId(), roleId);
		userRoleRepository.delete(ur);
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean removeAllRoleFromUser(String username) {
		User u = getUserByUsernameOrId(username);
		userRoleRepository.delete(u.getRoles());
		
		return true;
	}

	@Override
	public Page<Role> findRoleByUser(String username, Pageable pageable) {
		User u = getUserByUsernameOrId(username);
		Page<UserRole> userRoles = userRoleRepository.findByUserId(u.getId(), pageable);
		
		List<Role> roles = new ArrayList<Role>();
		for (UserRole ur : userRoles) {
			roles.add(ur.getRole());
		}
		
		return new PageImpl<Role>(roles, pageable, userRoles.getTotalElements());
	}

	@Override
	public Response getUserPhoto(String username) throws FileNotFoundException {
		User u = getUserByUsernameOrId(username);
		FileInfo info = u.getFileInfo();
		
		if (info != null) {
			String filePath = info.getPath();
			File file = new File(filePath);
			if (file.exists()) {
				return Response.ok(file, info.getContentType()).build();
			}
		}
		
		try {
			File file = context.getResource(profilePicPath).getFile();
			return Response.ok(file, profilePicContentType).build();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		
		return null;
	}

	@Override
	@Transactional
	public boolean updateUserPhoto(String username, InputStream inputStream) throws IOException {
		User u = getUserByUsernameOrId(username);
		String uploadPath = StringUtils.join(this.uploadPath, "/", u.getId());
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setDataBlob(inputStream);
		fileInfo.setContentType(request.getContentType());
		fileInfo = fileInfoService.saveFileInfo(uploadPath, fileInfo);
		
		u.setFileInfo(fileInfo);
		
		return true;
	}
}
