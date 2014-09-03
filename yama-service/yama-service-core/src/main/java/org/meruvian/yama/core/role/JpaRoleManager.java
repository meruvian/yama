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
package org.meruvian.yama.core.role;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.meruvian.yama.core.LogInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 *
 */
@Transactional(readOnly = true)
public class JpaRoleManager implements RoleManager {
	private JpaRoleRepository roleRepository;

	@PostConstruct
	public void postConstruct() {
		Validate.notNull(roleRepository, "JpaRoleRepository must be provided");
	}
	
	public void setRoleRepository(JpaRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role getRoleById(String id) {
		return roleRepository.findById(id);
	}

	@Override
	public Role getRoleByName(String rolename) {
		return roleRepository.findByName(rolename);
	}

	@Override
	public Page<JpaRole> findRoleByKeyword(String keyword, Pageable pageable) {
		keyword = StringUtils.defaultIfBlank(keyword, "");
		
		return roleRepository.findByNameContainsOrDescriptionContains(keyword, keyword, pageable);
	}

	@Override
	public Page<JpaRole> findActiveRoleByKeyword(String keyword, Pageable pageable) {
		keyword = StringUtils.defaultIfBlank(keyword, "");
		
		return roleRepository.findByNameContainsOrDescriptionContainsAndLogInformationActiveFlag(keyword, keyword, LogInformation.ACTIVE, pageable);
	}

	@Override
	@Transactional
	public boolean removeRole(Role role) {
		if (role == null)
			return false;
		
		roleRepository.delete(role.getId());
		
		return true;
	}

	@Override
	@Transactional
	public Role saveRole(Role role) {
		JpaRole jpaRole = null;
		
		if (StringUtils.isBlank(role.getId())) {
			jpaRole = new JpaRole(role.getName(), role.getDescription());
			roleRepository.save(jpaRole);
		} else {
			jpaRole = roleRepository.findById(role.getId());
			jpaRole.setName(role.getName());
			jpaRole.setDescription(role.getDescription());
		}
		
		return jpaRole;
	}

	@Override
	@Transactional
	public Role updateStatus(Role role, int status) {
		role = roleRepository.findById(role.getId());
		role.getLogInformation().setActiveFlag(status);
		
		return role;
	}
}
