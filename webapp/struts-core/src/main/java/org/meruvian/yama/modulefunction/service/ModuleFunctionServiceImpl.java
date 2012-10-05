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

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.modulefunction.ModuleFunction;
import org.meruvian.yama.modulefunction.dao.ModuleFunctionDomain;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dian Aditya
 * 
 */
@Service
@Transactional(readOnly = true)
public class ModuleFunctionServiceImpl implements ModuleFunctionService {
	@Inject
	private ModuleFunctionDomain moduleFunctionDomain;

	@Transactional
	public ModuleFunction saveModuleFunction(ModuleFunction moduleFunction) {
		String treePath = "";
		String url = "";

		if (moduleFunction.getMaster() != null) {
			ModuleFunction master = moduleFunction.getMaster();
			if (StringUtils.isBlank(master.getId())) {
				moduleFunction.setMaster(null);
			} else {
				master = moduleFunctionDomain.load(master.getId());
				moduleFunction.setMaster(master);
				treePath = master.getTreePath();
				url = org.meruvian.inca.struts2.rest.commons.StringUtils
						.prependWithSlash(master.getModuleUrl());
			}
		}

		if (StringUtils.isBlank(moduleFunction.getId())) {
			moduleFunction.setId(null);
			moduleFunction.setLogInformation(new LogInformation());

			moduleFunctionDomain.persist(moduleFunction);
		} else {
			ModuleFunction m = moduleFunctionDomain
					.load(moduleFunction.getId());
			m.setName(moduleFunction.getName());
			m.setDescription(moduleFunction.getDescription());
			m.setModuleUrl(moduleFunction.getModuleUrl());
			m.setMaster(moduleFunction.getMaster());

			m.getLogInformation().setUpdateDate(new Date());

			moduleFunction = m;
		}

		treePath = StringUtils.isBlank(treePath) ? "" : (treePath + ".");
		moduleFunction.setTreePath(treePath + moduleFunction.getId());
		moduleFunction.setModuleUrl(url
				+ org.meruvian.inca.struts2.rest.commons.StringUtils
						.prependWithSlash(moduleFunction.getModuleUrl()));

		return moduleFunction;
	}

	@Transactional
	public void deleteModuleFunction(ModuleFunction moduleFunction) {
		moduleFunction = moduleFunctionDomain.load(moduleFunction.getId());
		moduleFunction.getLogInformation().setStatusFlag(StatusFlag.INACTIVE);
	}

	public ModuleFunction getModuleFunctionById(String id) {
		return moduleFunctionDomain.findById(id);
	}

	public EntityListWrapper<ModuleFunction> findModuleFunctions(String name,
			String description, String moduleUrl, String orderby, String order,
			int max, int page) {
		return moduleFunctionDomain.findModuleFunction(name, description,
				moduleUrl, orderby, order, max, page, "AND");
	}

	public EntityListWrapper<ModuleFunction> findModuleFunctions(String field,
			int max, String orderBy, String order, int page) {
		return moduleFunctionDomain.findModuleFunction(field, field, field,
				orderBy, order, max, page, "OR");
	}

	public EntityListWrapper<ModuleFunction> findChilds(String masterId,
			String field, int max, String orderby, String order, int page) {
		return moduleFunctionDomain.findChilds(masterId, field, orderby, order,
				max, page);
	}

	public EntityListWrapper<ModuleFunction> findMasters(String masterId,
			String field, int max, String orderby, String order, int page) {
		return moduleFunctionDomain.findMasters(masterId, field, orderby,
				order, max, page);
	}

}
