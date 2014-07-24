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

import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.meruvian.yama.repository.application.Application;
import org.meruvian.yama.repository.jpa.application.JpaApplication;
import org.meruvian.yama.repository.jpa.application.JpaApplicationRepository;
import org.meruvian.yama.repository.jpa.user.JpaUserRepository;
import org.meruvian.yama.repository.user.User;
import org.meruvian.yama.service.ApplicationManager;
import org.meruvian.yama.service.SessionCredential;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 *
 */
@Service
@Transactional(readOnly = true)
public class JpaApplicationManager implements ApplicationManager {
	private JpaApplicationRepository applicationRepository;
	private JpaUserRepository userRepository;
	private SessionCredential sessionCredential;
	private StringEncryptor encryptor;
	
	@Inject
	public void setApplicationRepository(JpaApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}
	
	@Inject
	public void setUserRepository(JpaUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Inject
	public void setSessionCredential(SessionCredential sessionCredential) {
		this.sessionCredential = sessionCredential;
	}
	
	@Inject
	public void setEncryptor(PBEStringEncryptor encryptor) {
		this.encryptor = encryptor;
	}
	
	@Override
	public JpaApplication getApplicationById(String id) {
		return applicationRepository.findById(id);
	}

	@Override
	public JpaApplication getApplicationByNamespace(String namespace) {
		return applicationRepository.findByNamespace(namespace);
	}

	@Override
	public Page<JpaApplication> findApplicationByNamespace(String namespace, Pageable pageable) {
		return applicationRepository.findByDisplayNameStartingWith(namespace, pageable);
	}
	
	public Page<JpaApplication> findUsersApplicationByName(String name, Pageable pageable) {
		String userId = sessionCredential.getCurrentUser().getId();
		
		return applicationRepository.findByLogInformationCreateByAndDisplayNameStartingWith(userId, name, pageable);
	}

	@Override
	public Page<JpaApplication> findApplicationByUser(String username, Pageable pageable) {
		User u = userRepository.findByUsername(username);
		
		return applicationRepository.findByLogInformationCreateBy(u.getId(), pageable);
	}

	@Override
	@Transactional
	public JpaApplication saveApplication(Application application) {
		JpaApplication jpaApplication = null;
		
		if (StringUtils.isBlank(application.getId())) {
			jpaApplication = new JpaApplication(application);
			jpaApplication.setSecret(generateSecret());
			applicationRepository.save(jpaApplication);
		} else {
			jpaApplication = applicationRepository.findById(application.getId());
			String secret = jpaApplication.getSecret();
			jpaApplication.update(application);
			jpaApplication.setSecret(secret);
		}
		
		return jpaApplication;
	}

	@Override
	@Transactional
	public boolean removeApplication(Application application) {
		if (application == null)
			return false;
		
		applicationRepository.delete(new JpaApplication(application));
		
		return true;
	}

	@Override
	@Transactional
	public JpaApplication generateNewSecret(Application application) {
		JpaApplication a = getApplicationById(application.getId());
		if (a != null) {
			a.setSecret(generateSecret());
		}
		
		return a;
	}
	
	private String generateSecret() {
		return encryptor.encrypt(UUID.randomUUID().toString());
	}
}
