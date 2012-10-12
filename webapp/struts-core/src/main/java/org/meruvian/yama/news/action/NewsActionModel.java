package org.meruvian.yama.news.action;

import org.meruvian.yama.news.News;
import org.meruvian.yama.persistence.EntityListWrapper;

public class NewsActionModel {
	
	private News news = new News();
	private EntityListWrapper<News> newses= new EntityListWrapper<News>();
	private int max = 5;
	private int page = 1;
	private String order;
	private String orderBy;
	private String q;
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	public EntityListWrapper<News> getNewses() {
		return newses;
	}
	public void setNewses(EntityListWrapper<News> newses) {
		this.newses = newses;
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
