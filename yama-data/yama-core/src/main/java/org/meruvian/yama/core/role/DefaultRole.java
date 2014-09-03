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
package org.meruvian.yama.core.role;

import org.meruvian.yama.core.LogInformation;

/**
 * @author Dian Aditya
 *
 */
public class DefaultRole implements Role {
	private String id;
	private LogInformation logInformation = new LogInformation();
	private String name;
	private String description;
	
	public DefaultRole() {}
	
	public DefaultRole(Role role) {
		this.id = role.getId();
		this.logInformation = role.getLogInformation();
		this.name = role.getName();
		this.description = role.getDescription();
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public LogInformation getLogInformation() {
		return logInformation;
	}

	public void setLogInformation(LogInformation logInformation) {
		this.logInformation = logInformation;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void update(Role role) {
		
	}
}
