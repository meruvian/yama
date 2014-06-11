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
package org.meruvian.yama.repository.jpa.commons;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.meruvian.yama.repository.commons.Name;

/**
 * @author Dian Aditya
 *
 */
@Embeddable
public class JpaName extends Name {
	public JpaName() {}
	
	public JpaName(Name name) {
		super(name.getPrefix(), name.getFirst(), name.getMiddle(), name.getLast());
	}
	
	@Override
	@Column(name = "name_first")
	public String getFirst() {
		return super.getFirst();
	}
	
	@Override
	@Column(name = "name_last")
	public String getLast() {
		return super.getLast();
	}
	
	@Override
	@Column(name = "name_middle")
	public String getMiddle() {
		return super.getMiddle();
	}
	
	@Override
	@Column(name = "name_prefix")
	public String getPrefix() {
		return super.getPrefix();
	}
}