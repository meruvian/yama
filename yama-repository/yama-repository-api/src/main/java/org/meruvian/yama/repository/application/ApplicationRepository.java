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
package org.meruvian.yama.repository.application;

import org.meruvian.yama.repository.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dian Aditya
 *
 */
public interface ApplicationRepository<T extends Application> extends DefaultRepository<T>{
	T findById(String id);
	
	T findByNamespace(String name);
	
	Page<T> findByNamespaceStartingWith(String name, Pageable pageable);
	
	Page<T> findByDisplayNameStartingWith(String name, Pageable pageable);
	
	Page<T> findByLogInformationCreateBy(String userId, Pageable pageable);
}
