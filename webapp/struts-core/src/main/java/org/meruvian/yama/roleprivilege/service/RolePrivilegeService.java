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
package org.meruvian.yama.roleprivilege.service;

import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.roleprivilege.RolePrivilege;

public interface RolePrivilegeService {
	void save(RolePrivilege rolePrivilege);

	void delete(RolePrivilege rolePrivilege);

	void delete(String roleId, String moduleFunctionId);

	RolePrivilege getRolePrivilegeById(String id);

	RolePrivilege findRolePrivilege(String roleId, String moduleFunctionId);

	EntityListWrapper<ModuleFunction> findModuleFunctionByRole(String roleId,
			String orderby, String order, int max, int page);

	EntityListWrapper<ModuleFunction> findModuleFunctionByRolePrivilege(
			String roleId, String field, String orderby, String order, int max,
			int page);
}
