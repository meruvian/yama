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
package org.meruvian.yama.service.jpa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.meruvian.yama.repository.LogInformation;
import org.meruvian.yama.repository.jpa.role.JpaRole;
import org.meruvian.yama.repository.jpa.role.JpaRoleRepository;
import org.meruvian.yama.repository.jpa.role.JpaUserRole;
import org.meruvian.yama.repository.jpa.role.JpaUserRoleRepository;
import org.meruvian.yama.repository.jpa.user.JpaUser;
import org.meruvian.yama.repository.jpa.user.JpaUserRepository;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.UserManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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
public class JpaUserManager implements UserManager {
	private JpaUserRepository userRepository;
	private JpaRoleRepository roleRepository;
	private JpaUserRoleRepository userRoleRepository;
	private PasswordEncoder passwordEncoder;
	
	@Value("${init.username}")
	private String defaultUsername;
	
	@Value("${init.password}")
	private String defaultPassword;

	@Value("${init.role}")
	private String defaultRole;
	
	@Value("${init.email}")
	private String defaultEmail;
	
	@Inject
	public void setUserRepository(JpaUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Inject
	public void setRoleRepository(JpaRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Inject
	public void setUserRoleRepository(JpaUserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}

	@Inject
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
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
	public Page<JpaUser> findUserByKeyword(String keyword, Pageable pageable) {
		keyword = StringUtils.defaultIfBlank(keyword, "");
		return userRepository.find(keyword, pageable);
	}

	@Override
	public Page<JpaUser> findActiveUserByKeyword(String keyword, Pageable pageable) {
		keyword = StringUtils.defaultIfBlank(keyword, "");
		return userRepository.find(keyword, LogInformation.ACTIVE, pageable);
	}

	@Override
	@Transactional
	public boolean removeUser(User user) {
		user = findUser(user);
		
		if (user == null)
			return false;
		
		user.getLogInformation().setActiveFlag(LogInformation.INACTIVE);
		
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
			jpaUser.update(user);
			if (StringUtils.isNotBlank(user.getPassword())) {
				jpaUser.setPassword(passwordEncoder.encode(user.getPassword()));
			}
		}
		
		return jpaUser;
	}

	@Override
	@Transactional
	public JpaUser updateUserPassword(User user, String currentPassword, String newPassword) {
		JpaUser jpaUser = findUser(user);
		if (passwordEncoder.matches(currentPassword, jpaUser.getPassword())) {
			jpaUser.setPassword(passwordEncoder.encode(newPassword));
			
			return jpaUser;
		}
		
		return null;
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
		
		JpaUserRole jpaUserRole = userRoleRepository.findByUserIdAndRoleid(user.getId(), role.getId());
		userRoleRepository.delete(jpaUserRole);
		
		return true;
	}

	@Override
	public Page<JpaRole> findRoleByUser(User user, Pageable pageable) {
		user = findUser(user);
		
		return roleRepository.findByUserId(user.getId(), pageable);
	}
	
	@PostConstruct
	public void initUser() {
		JpaUser user = getUserByUsername(defaultUsername);
		if (user == null) {
			user = new JpaUser();
			user.setUsername(defaultUsername);
			user.setPassword(passwordEncoder.encode(defaultPassword));
			user.setEmail(defaultEmail);
			
			saveUser(user);
			
			JpaRole role = new JpaRole();
			role.setName(defaultRole);
			roleRepository.save(role);
			
			addRoleToUser(user, role);
		}
	}

	private JpaUser findUser(User user) {
		if (StringUtils.isNotBlank(user.getId())) {
			return userRepository.findById(user.getId());
		}
		
		return userRepository.findByUsername(user.getUsername());
	}
	
	private JpaRole findRole(Role role) {
		if (StringUtils.isNotBlank(role.getId())) {
			return roleRepository.findById(role.getId());
		}
		
		return roleRepository.findByName(role.getName());
	}
}
