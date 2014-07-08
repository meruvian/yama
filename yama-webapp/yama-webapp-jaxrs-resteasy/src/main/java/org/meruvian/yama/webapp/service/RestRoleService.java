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
package org.meruvian.yama.webapp.service;

import javax.inject.Inject;

import org.jboss.resteasy.annotations.Form;
import org.meruvian.yama.repository.role.DefaultRole;
import org.meruvian.yama.repository.role.Role;
import org.meruvian.yama.service.RoleManager;
import org.meruvian.yama.service.jaxrs.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Dian Aditya
 *
 */
@Service
public class RestRoleService implements RoleService{
	@Inject
	private RoleManager roleManager;
	
	@Override
	public Role getRoleByName(String name) {
		return roleManager.getRoleByName(name);
	}

	@Override
	public Page<? extends Role> findRoleByKeyword(String keyword, int max, int page) {
		return roleManager.findActiveRoleByKeyword(keyword, new PageRequest(page, max));
	}

	@Override
	public boolean removeRole(final String name) {
		return roleManager.removeRole(new DefaultRole() {{ setName(name); }});
	}

	@Override
	public Role saveRole(@Form DefaultRole role) {
		return null;
	}

}
