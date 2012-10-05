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
package org.meruvian.yama.persistence;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * @author Dian Aditya
 * 
 */
//@MappedSuperclass
//public class SelfReferenceEntity<T extends SelfReferenceEntity> extends
//		DefaultPersistence {
//	private T master;
//	private String treePath;
//
//	@JsonBackReference
//	@ManyToOne
//	@JoinColumn(name = "iparent")
//	public T getMaster() {
//		return master;
//	}
//
//	public void setMaster(T master) {
//		this.master = master;
//	}
//
//	@Column(name = "tree_path", length = 500)
//	public String getTreePath() {
//		return treePath;
//	}
//
//	public void setTreePath(String treePath) {
//		this.treePath = treePath;
//	}
//}
