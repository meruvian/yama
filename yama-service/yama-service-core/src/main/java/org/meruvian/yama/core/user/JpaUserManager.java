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
package org.meruvian.yama.core.user;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.meruvian.yama.core.LogInformation;
import org.meruvian.yama.core.commons.FileInfo;
import org.meruvian.yama.core.commons.JpaFileInfo;
import org.meruvian.yama.core.commons.JpaFileInfoRepository;
import org.meruvian.yama.core.role.JpaRole;
import org.meruvian.yama.core.role.JpaRoleRepository;
import org.meruvian.yama.core.role.JpaUserRole;
import org.meruvian.yama.core.role.JpaUserRoleRepository;
import org.meruvian.yama.core.role.Role;
import org.meruvian.yama.core.user.JpaUser;
import org.meruvian.yama.core.user.JpaUserRepository;
import org.meruvian.yama.core.user.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Transactional(readOnly = true)
public class JpaUserManager implements UserManager {
	private JpaUserRepository userRepository;
	private JpaRoleRepository roleRepository;
	private JpaUserRoleRepository userRoleRepository;
	private JpaFileInfoRepository fileInfoRepository;
	private PasswordEncoder passwordEncoder;
	private Properties properties;
	
	private String defaultUsername;
	private String defaultPassword;
	private String defaultRole;
	private String defaultEmail;
	
	@PostConstruct
	public void postConstruct() {
		Validate.notNull(userRepository, "JpaUserRepository must be provided");
		Validate.notNull(roleRepository, "JpaRoleRepository must be provided");
		Validate.notNull(userRoleRepository, "JpaUserRoleRepository must be provided");
		Validate.notNull(fileInfoRepository, "JpaFileInfoRepository must be provided");
		Validate.notNull(passwordEncoder, "Password encoder must be set");
		Validate.notNull(properties, "Properties must be set");
		
		this.defaultUsername = properties.getProperty("init.username");
		this.defaultPassword = properties.getProperty("init.password");
		this.defaultRole = properties.getProperty("init.role.admin");
		this.defaultEmail = properties.getProperty("init.email");
	}
	
	
	public void setUserRepository(JpaUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void setRoleRepository(JpaRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public void setUserRoleRepository(JpaUserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}
	
	public void setFileInfoRepository(JpaFileInfoRepository fileInfoRepository) {
		this.fileInfoRepository = fileInfoRepository;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public JpaUser getUserById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public JpaUser getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Page<JpaUser> findUserByKeyword(String keyword, Pageable pageable) {
		keyword = StringUtils.defaultIfBlank(keyword, "");
		return userRepository.findByUsernameStartingWith(keyword, pageable);
	}

	@Override
	public Page<JpaUser> findActiveUserByKeyword(String keyword, Pageable pageable) {
		keyword = StringUtils.defaultIfBlank(keyword, "");
		return userRepository.findByUsernameStartingWithAndLogInformationActiveFlag(keyword, LogInformation.ACTIVE, pageable);
	}

	@Override
	@Transactional
	public boolean removeUser(User user) {
		JpaUser u = findUser(user);
		
		if (u == null)
			return false;
		
		userRepository.delete(u);
		
		return true;
	}

	@Override
	@Transactional
	public JpaUser saveUser(User user) {
		JpaUser jpaUser = null;
		
		if (StringUtils.isBlank(user.getId())) {
			jpaUser = new JpaUser(user);
			if (StringUtils.isNotBlank(user.getPassword())) {
				jpaUser.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			userRepository.save(jpaUser);
		} else {
			jpaUser = userRepository.findById(user.getId());
			String originalPasswd = jpaUser.getPassword();
			jpaUser.update(user);
			
			if (StringUtils.isNotBlank(user.getPassword())) {
				jpaUser.setPassword(passwordEncoder.encode(user.getPassword()));
			} else {
				jpaUser.setPassword(originalPasswd);
			}
		}
		
		return jpaUser;
	}

	@Override
	@Transactional
	public JpaUser updateUserPassword(User user, String currentPassword, String newPassword) {
		JpaUser jpaUser = findUser(user);
		if (currentPassword != null) {
			if (passwordEncoder.matches(currentPassword, jpaUser.getPassword())) {
				jpaUser.setPassword(passwordEncoder.encode(newPassword));
			}
		} else {
			jpaUser.setPassword(passwordEncoder.encode(newPassword));
		}
		
		return jpaUser;
	}

	@Override
	@Transactional
	public boolean addRoleToUser(User user, Role role) {
		JpaUser jpaUser = findUser(user);
		JpaRole jpaRole = findRole(role);
		
		JpaUserRole jpaUserRole = new JpaUserRole(jpaRole, jpaUser);
		try {
			return userRoleRepository.save(jpaUserRole) != null;
		} catch (DataIntegrityViolationException e) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeRoleFromUser(User user, Role role) {
		user = findUser(user);
		role = findRole(role);
		
		JpaUserRole jpaUserRole = userRoleRepository.findByUserIdAndRoleId(user.getId(), role.getId());
		userRoleRepository.delete(jpaUserRole);
		
		return true;
	}

	@Override
	public Page<JpaUserRole> findRoleByUser(User user, Pageable pageable) {
		user = findUser(user);
		
		return userRoleRepository.findByUserId(user.getId(), pageable);
	}
	
	public void initUser() {
		JpaUser user = getUserByUsername(defaultUsername);
		if (user == null) {
			user = new JpaUser();
			user.setUsername(defaultUsername);
			user.setPassword(defaultPassword);
			user.setEmail(defaultEmail);
			user.setFileInfo(null);
			
			saveUser(user);
			
			JpaRole role = new JpaRole();
			role.setName(defaultRole);
			roleRepository.save(role);
			
			addRoleToUser(user, role);
		}
	}

	@Override
	@Transactional
	public FileInfo setUserProfilePicture(User user, FileInfo fileInfo) {
		JpaUser u = findUser(user);
		
		JpaFileInfo file = new JpaFileInfo(fileInfo);
		file = fileInfoRepository.findOne(fileInfo.getId());
		
		u.setFileInfo(file);
		
		return file;
	}

	@Override
	public JpaUser findUser(User user) {
		if (StringUtils.isNotBlank(user.getId())) {
			return userRepository.findById(user.getId());
		}
		
		if (StringUtils.isNotBlank(user.getEmail())) {
			return userRepository.findByEmail(user.getEmail());
		}
		
		return userRepository.findByUsername(user.getUsername());
	}
	
	@Override
	@Transactional
	public User updateStatus(User user, int status) {
		user = userRepository.findById(user.getId());
		user.getLogInformation().setActiveFlag(status);
		
		return user;
	}
	
	private JpaRole findRole(Role role) {
		if (StringUtils.isNotBlank(role.getId())) {
			return roleRepository.findById(role.getId());
		}
		
		return roleRepository.findByName(role.getName());
	}
}
