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
package org.meruvian.yama.roleprivilege.action;

import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.role.Role;

public class RolePrivilegeActionModel {
	private Role role = new Role();
	private ModuleFunction moduleFunction = new ModuleFunction();
	private EntityListWrapper<ModuleFunction> moduleFunctions = new EntityListWrapper<ModuleFunction>();
	private int max = 10;
	private int page = 1;
	private String order = "ASC";
	private String orderBy;
	private String q;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ModuleFunction getModuleFunction() {
		return moduleFunction;
	}

	public void setModuleFunction(ModuleFunction moduleFunction) {
		this.moduleFunction = moduleFunction;
	}

	public EntityListWrapper<ModuleFunction> getModuleFunctions() {
		return moduleFunctions;
	}

	public void setModuleFunctions(
			EntityListWrapper<ModuleFunction> moduleFunctions) {
		this.moduleFunctions = moduleFunctions;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

}
