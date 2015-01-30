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
 * WISocialConnectionHOUSocialConnection WARRANSocialConnectionIES OR CONDISocialConnectionIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.meruvian.yama.social.connection;

import java.util.Collection;
import java.util.List;

import org.meruvian.yama.core.DefaultRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 *
 */
@Repository
public interface SocialConnectionRepository extends DefaultRepository<SocialConnection> {
	List<SocialConnection> findByUserIdOrderByRankAsc(String userId);
	
	List<SocialConnection> findByUserIdAndProviderOrderByRankAsc(String userId, String provider);
	
	SocialConnection findByUserIdAndProviderAndProviderUserId(String userId, String provider, String providerUserId);
	
	List<SocialConnection> findByUserIdAndProvider(String userId, String provider);
	
	List<SocialConnection> findByUserIdAndProviderAndRank(String userId, String provider, int rank);
	
	@Query("SELECT c.user.id FROM SocialConnection c WHERE c.provider = ?1 AND c.providerUserId = ?2")
	List<String> findUserIdByProviderAndProviderUserId(String provider, String providerUserId);


	@Query("SELECT c.user.id FROM SocialConnection c WHERE c.provider = ?1 AND c.providerUserId IN ?2")
	List<String> findUserIdByProviderAndProviderUserIdIn(String provider, Collection<String> providerUserIds);
	
	int findRankByUserId(String userId, String provider);

	@Query("SELECT COALESCE(MAX(c.rank) + 1, 1) FROM SocialConnection c WHERE c.providerUserId = ?1 AND c.provider = ?2")
	int getRank(String userId, String provider);
}
