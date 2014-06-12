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
package org.meruvian.yama.repository.jpa.social;

import org.meruvian.yama.repository.social.SocialConnectionRepository;
import org.meruvian.yama.repository.social.SocialConnection.Provider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Dian Aditya
 *
 */
@Repository
public interface JpaSocialConnectionRepository extends SocialConnectionRepository<JpaSocialConnection> {

	@Override
	@Query("SELECT coalesce(max(c.rank) + 1, 1) as rank FROM JpaSocialConnection c WHERE c.user.id = ?1 AND c.provider = ?2")
	public int getRank(String userId, Provider provider);
}
