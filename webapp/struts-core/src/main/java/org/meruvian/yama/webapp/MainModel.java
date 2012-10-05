package org.meruvian.yama.webapp;


public class MainModel {
	private int max = 10;
	private int page = 1;
	private String order = "ASC";
	private String orderBy;
	private String q;

	
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
