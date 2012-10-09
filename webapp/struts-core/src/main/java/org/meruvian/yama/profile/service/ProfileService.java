package org.meruvian.yama.profile.service;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.security.User;

public interface ProfileService {

	User save(User user);

	User savePass(User user);

	User getUserById(String id);

	EntityListWrapper<User> findCategoryByEmail(String id, String email,
			String username, int max, int page);

}
