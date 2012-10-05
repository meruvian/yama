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
package org.meruvian.yama.modulefunction.service;

import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.persistence.EntityListWrapper;

/**
 * @author Dian Aditya
 * 
 */
public interface ModuleFunctionService {
	ModuleFunction saveModuleFunction(ModuleFunction moduleFunction);

	void deleteModuleFunction(ModuleFunction moduleFunction);

	ModuleFunction getModuleFunctionById(String id);

	EntityListWrapper<ModuleFunction> findModuleFunctions(String name,
			String description, String moduleUrl, String orderby, String order,
			int max, int page);

	EntityListWrapper<ModuleFunction> findModuleFunctions(String field,
			int max, String orderBy, String order, int page);

	EntityListWrapper<ModuleFunction> findChilds(String masterId, String field,
			int max, String orderBy, String order, int page);

	EntityListWrapper<ModuleFunction> findMasters(String masterId,
			String field, int max, String orderby, String order, int page);
}