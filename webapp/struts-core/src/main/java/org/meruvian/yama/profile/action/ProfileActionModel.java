package org.meruvian.yama.profile.action;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.security.User;

public class ProfileActionModel {

	private User user = new User();
	private EntityListWrapper<User> users = new EntityListWrapper<User>();
	private int max = 10;
	private int page = 1;
	private String order;
	private String orderBy;
	private String q;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public EntityListWrapper<User> getUsers() {
		return users;
	}

	public void setUsers(EntityListWrapper<User> users) {
		this.users = users;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

}
