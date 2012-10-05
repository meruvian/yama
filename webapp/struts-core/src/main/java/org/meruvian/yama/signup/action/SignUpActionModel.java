package org.meruvian.yama.signup.action;

import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.security.User;

public class SignUpActionModel {
	
	private User signUp = new User();
	private EntityListWrapper<User> signUps= new EntityListWrapper<User>();
	private int max = 10;
	private int page = 1;
	private String order;
	private String orderBy;
	private String q;
	
	public User getSignUp() {
		return signUp;
	}
	public void setSignUp(User signUp) {
		this.signUp = signUp;
	}
	public EntityListWrapper<User> getSignUps() {
		return signUps;
	}
	public void setSignUps(EntityListWrapper<User> signUps) {
		this.signUps = signUps;
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
