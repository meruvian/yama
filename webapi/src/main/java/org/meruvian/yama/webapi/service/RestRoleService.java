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

import javax.inject.Inject;

import org.meruvian.yama.core.role.Role;
import org.meruvian.yama.core.role.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class RestRoleService implements RoleService {
	@Inject
	private RoleRepository roleRepository;
	
	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public Page<Role> findRoleByKeyword(String keyword, int max, int page) {
		return roleRepository.findByNameContainsOrDescriptionContains(keyword, keyword, new PageRequest(page, max));
	}

	@Override
	public void removeRole(final String name) {
		roleRepository.delete(getRoleByName(name));
	}

	@Override
	public Role saveRole(Role role) {
		return null;
	}

}
