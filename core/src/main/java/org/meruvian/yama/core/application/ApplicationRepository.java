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
 * WIApplicationHOUApplication WARRANApplicationIES OR CONDIApplicationIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.core.application;

import org.meruvian.yama.core.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


/**
 * @author Dian Aditya
 *
 */
@Repository
public interface ApplicationRepository extends DefaultRepository<Application> {
	Application findByNamespace(String name);
	
	Page<Application> findByNamespaceStartingWith(String name, Pageable pageable);
	
	Page<Application> findByDisplayNameStartingWith(String name, Pageable pageable);
	
	Page<Application> findByLogInformationCreateBy(String userId, Pageable pageable);
	
	Page<Application> findByLogInformationCreateByAndDisplayNameStartingWith(String userId, String name, Pageable pageable);
}
