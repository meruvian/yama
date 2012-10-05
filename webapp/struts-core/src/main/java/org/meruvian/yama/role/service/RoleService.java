/**
 * Copyright 2012 Meruvian
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
package org.meruvian.yama.role.service;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.role.Role;

/**
 * @author Dian Aditya
 * 
 */
public interface RoleService {
	Role saveRoleLocation(Role role);

	Role saveRole(Role role);

	void deleteRole(Role role);

	Role getRoleById(String id);

	EntityListWrapper<Role> findRoles(String name, String description,
			String orderby, String order, int max, int page);

	EntityListWrapper<Role> findRoles(String field, int max, String orderBy,
			String order, int page);

	EntityListWrapper<Role> findRoles(String field, Role createBy,
			String orderby, String order, int max, int page);

	EntityListWrapper<Role> findMastersForRole(String masterId, String field,
			String orderby, String order, int max, int page);
}