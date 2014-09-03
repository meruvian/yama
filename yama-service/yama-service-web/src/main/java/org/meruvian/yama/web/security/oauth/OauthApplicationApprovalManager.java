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
package org.meruvian.yama.web.security.oauth;

import org.meruvian.yama.security.oauth.OauthApplicationApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

/**
 * @author Dian Aditya
 *
 */
public interface OauthApplicationApprovalManager extends ApprovalStore {
	OauthApplicationApproval getApprovalById(String id);
	
	OauthApplicationApproval getApprovalByUserAndClientAndScope(String userId, String clientId, String scope);
	
	Page<? extends OauthApplicationApproval> findApprovalByUserAndClient(String userId, String clientId, Pageable pageable);
	
	Page<? extends OauthApplicationApproval> findApprovalByClient(String clientId, Pageable pageable);
	
	Page<? extends OauthApplicationApproval> findApprovalByUser(String userId, Pageable pageable);
}
