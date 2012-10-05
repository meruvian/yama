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
package org.meruvian.yama.modulefunction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.meruvian.yama.persistence.DefaultPersistence;

/**
 * @author Amelia
 * @since 1.0
 * 
 */
@Entity
@Table(name = "yama_module_function")
public class ModuleFunction extends DefaultPersistence {
	private String name;
	private String description;
	private int viewActive;
	private String tableReferences;
	private List<ModuleFunction> moduleFunctions = new ArrayList<ModuleFunction>();
	// private ModuleFunction master;
	private String moduleUrl;
	private ModuleFunction master;
	private String treePath;

	// private String treePath = "";

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length = 128)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ref_desc")
	public String getTableReferences() {
		return tableReferences;
	}

	public void setTableReferences(String tableReferences) {
		this.tableReferences = tableReferences;
	}

	@Column(name = "viewall_flag", length = 1)
	public int getViewActive() {
		return viewActive;
	}

	public void setViewActive(int viewActive) {
		this.viewActive = viewActive;
	}

	// @ManyToOne
	// @JoinColumn(name = "master", nullable = true, updatable = true,
	// insertable = true)
	// public ModuleFunction getMaster() {
	// return master;
	// }
	//
	// public void setMaster(ModuleFunction master) {
	// this.master = master;
	// }

	@JsonIgnore
	@OneToMany(mappedBy = "master", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<ModuleFunction> getModuleFunctions() {
		return moduleFunctions;
	}

	public void setModuleFunctions(List<ModuleFunction> moduleFunctions) {
		this.moduleFunctions = moduleFunctions;
	}

	@Column(name = "module_url")
	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	// @Column(name = "tree_path", length = 500)
	// public String getTreePath() {
	// return treePath;
	// }
	//
	// public void setTreePath(String treePath) {
	// this.treePath = treePath;
	// }

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "iparent")
	public ModuleFunction getMaster() {
		return master;
	}

	public void setMaster(ModuleFunction master) {
		this.master = master;
	}

	@Column(name = "tree_path", length = 500)
	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}
}
