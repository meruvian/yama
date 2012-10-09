package org.meruvian.yama.profile.service;

import java.util.Date;

import javax.inject.Inject;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.profile.dao.ProfileDomain;
import org.meruvian.yama.security.SessionCredentials;
import org.meruvian.yama.security.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

	@Inject
	private ProfileDomain profileDomain;

	@Transactional
	public User save(User user) {
		// TODO Auto-generated method stub
		User s = profileDomain.load(SessionCredentials.currentUser().getId());
		s.setId(s.getId());
		s.setUsername(s.getUsername());
		s.setPassword(s.getPassword());
		s.setName(user.getName().equals(null) ? s.getName() : user.getName());
		s.setAddress(user.getAddress().equals(null) ? s.getAddress() : user
				.getAddress());
		s.setEmail(user.getEmail().equals(null) ? s.getEmail() : user
				.getEmail());
		s.setDescription(s.getDescription());

		s.getLogInformation().setUpdateDate(new Date());

		return s;
	}

	@Transactional
	public User savePass(User user) {
		// TODO Auto-generated method stub
		User s = profileDomain.load(SessionCredentials.currentUser().getId());
		s.setId(s.getId());
		s.setUsername(s.getUsername());
		s.setPassword(user.getPassword().equals(null) ? s.getPassword() : user
				.getPassword());
		s.setName(s.getName());
		s.setAddress(s.getAddress());
		s.setEmail(s.getEmail());
		s.setDescription(s.getDescription());

		s.getLogInformation().setUpdateDate(new Date());

		return s;
	}

	public User getUserById(String id) {
		return profileDomain.findById(id);
	}

	public EntityListWrapper<User> findCategoryByEmail(String id, String email,
			String username, int max, int page) {
		return profileDomain
				.findCategoryByEmail(id, email, username, max, page);
	}

}
