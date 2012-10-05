package org.meruvian.yama.signup.service;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation;
import org.meruvian.yama.security.User;
import org.meruvian.yama.signup.dao.SignUpDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SignUpServiceImpl implements SignUpService {

	@Inject
	private SignUpDomain signUpDomain;

	@Transactional
	public User signUp(User signUp) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(signUp.getId())) {
			signUp.setId(null);
			signUp.setLogInformation(new LogInformation());

			signUpDomain.persist(signUp);
		} else {
			User s = signUpDomain.load(signUp.getId());
			s.setUsername(signUp.getUsername());
			s.setPassword(signUp.getPassword());
			s.setName(signUp.getName());
			s.setAddress(signUp.getAddress());
			s.setEmail(signUp.getEmail());
			s.setDescription(signUp.getDescription());
			s.setWorkspaceType(signUp.getWorkspaceType());

			s.getLogInformation().setUpdateDate(new Date());

			return s;
		}
		return signUp;
	}

	public EntityListWrapper<User> findCategoryByEmail(String email,
			String username, int max, int page) {
		return signUpDomain.findCategoryByEmail(email, username, max, page);
	}

}
