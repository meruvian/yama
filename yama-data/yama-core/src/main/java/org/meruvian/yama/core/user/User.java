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
package org.meruvian.yama.core.user;

import org.meruvian.yama.core.DefaultPersistence;
import org.meruvian.yama.core.Updateable;
import org.meruvian.yama.core.commons.Address;
import org.meruvian.yama.core.commons.FileInfo;
import org.meruvian.yama.core.commons.Name;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dian Aditya
 * 
 */
public interface User extends DefaultPersistence, Updateable<User> {
	String getUsername();

	Name getName();
	
	@JsonIgnore
	String getPassword();

	String getEmail();
	
	Address getAddress();
	
	@JsonIgnore
	FileInfo getFileInfo();
}
