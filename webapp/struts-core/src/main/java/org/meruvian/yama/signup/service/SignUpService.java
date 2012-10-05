package org.meruvian.yama.signup.service;


import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.security.User;

public interface SignUpService {

	User signUp(User signUp);

	EntityListWrapper<User> findCategoryByEmail(String email, String username,
			int max, int page);

}
