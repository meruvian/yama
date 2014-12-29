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
 * WIOauthApplicationApprovalHOUOauthApplicationApproval WARRANOauthApplicationApprovalIES OR CONDIOauthApplicationApprovalIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.web.security.oauth;

import org.meruvian.yama.core.DefaultRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 *
 */
@Repository
public interface OauthApplicationApprovalRepository extends DefaultRepository<OauthApplicationApproval> {
	OauthApplicationApproval findByLogInformationCreateByAndApplicationIdAndScope(String userId, String clientId, String scope);
	
	Page<OauthApplicationApproval> findByLogInformationCreateByAndApplicationId(String userId, String clientId, Pageable pageable);
	
	Page<OauthApplicationApproval> findByApplicationId(String clientId, Pageable pageable);
	
	Page<OauthApplicationApproval> findByLogInformationCreateBy(String userId, Pageable pageable);
}
