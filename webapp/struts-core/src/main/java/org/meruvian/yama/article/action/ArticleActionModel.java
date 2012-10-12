package org.meruvian.yama.article.action;

import org.meruvian.yama.article.Article;
import org.meruvian.yama.persistence.EntityListWrapper;

public class ArticleActionModel {
	
	private Article article = new Article();
	private EntityListWrapper<Article> articles= new EntityListWrapper<Article>();
	private int max = 5;
	private int page = 1;
	private String order;
	private String orderBy;
	private String q;
	
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public EntityListWrapper<Article> getArticles() {
		return articles;
	}
	public void setArticles(EntityListWrapper<Article> articles) {
		this.articles = articles;
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
