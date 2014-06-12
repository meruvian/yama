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
package org.meruvian.yama.repository.social;

import java.util.Collection;
import java.util.List;

import org.meruvian.yama.repository.DefaultRepository;
import org.meruvian.yama.repository.social.SocialConnection.Provider;

/**
 * @author Dian Aditya
 *
 */
public interface SocialConnectionRepository<T extends SocialConnection> extends DefaultRepository<T> {
	List<T> findByUserIdOrderByRankAsc(String userId);
	
	List<T> findByUserIdAndProviderOrderByRankAsc(String userId, Provider provider);
	
	T findByUserIdAndProviderAndProviderUserId(String userId, Provider provider, String providerUserId);
	
	List<T> findByUserIdAndProvider(String userId, Provider provider);
	
	List<T> findByUserIdAndProviderAndRank(String userId, Provider provider, int rank);
	
	int getRank(String userId, Provider provider);
	
	List<String> findUserIdByProviderAndProviderUserId(Provider provider, String providerUserId);

	List<String> findUserIdByProviderAndProviderUserIdIn(Provider provider, Collection<String> providerUserIds);
}
