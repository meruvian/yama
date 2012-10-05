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
package org.meruvian.yama.role;

import java.util.ArrayList;
import java.util.List;

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
 * @author ginanjar
 * @since 1.0
 * 
 */
@Entity
@Table(name = "yama_workflow_role")
public class Role extends DefaultPersistence {
	private String name;
	private String description;
	private String workspaceType;
	private List<Role> roles = new ArrayList<Role>();
	private Role master;
	private String treePath;

	@Column(length = 125)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "workspace_type")
	public String getWorkspaceType() {
		return workspaceType;
	}

	public void setWorkspaceType(String workspaceType) {
		this.workspaceType = workspaceType;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "master", fetch = FetchType.LAZY)
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "iparent")
	public Role getMaster() {
		return master;
	}

	public void setMaster(Role master) {
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
