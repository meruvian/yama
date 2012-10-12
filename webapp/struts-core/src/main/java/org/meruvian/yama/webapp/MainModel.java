package org.meruvian.yama.webapp;

import org.meruvian.yama.article.Article;
import org.meruvian.yama.news.News;
import org.meruvian.yama.persistence.EntityListWrapper;


public class MainModel {
	
	private News news = new News();
	private EntityListWrapper<News> newses= new EntityListWrapper<News>();
	private Article article = new Article();
	private EntityListWrapper<Article> articles= new EntityListWrapper<Article>();
	private int maxNews = 5;
	private int pageNews = 1;
	private String orderNews = "ASC";
	private String orderByNews;
	private int maxArticle = 9;
	private int pageArticle = 1;
	private String orderArticle = "ASC";
	private String orderByArticle;
	private String q;

	
	public int getMaxNews() {
		return maxNews;
	}

	public void setMaxNews(int maxNews) {
		this.maxNews = maxNews;
	}

	public int getPageNews() {
		return pageNews;
	}

	public void setPageNews(int pageNews) {
		this.pageNews = pageNews;
	}

	public String getOrderNews() {
		return orderNews;
	}

	public void setOrderNews(String orderNews) {
		this.orderNews = orderNews;
	}

	public String getOrderByNews() {
		return orderByNews;
	}

	public void setOrderByNews(String orderByNews) {
		this.orderByNews = orderByNews;
	}

	public int getMaxArticle() {
		return maxArticle;
	}

	public void setMaxArticle(int maxArticle) {
		this.maxArticle = maxArticle;
	}

	public int getPageArticle() {
		return pageArticle;
	}

	public void setPageArticle(int pageArticle) {
		this.pageArticle = pageArticle;
	}

	public String getOrderArticle() {
		return orderArticle;
	}

	public void setOrderArticle(String orderArticle) {
		this.orderArticle = orderArticle;
	}

	public String getOrderByArticle() {
		return orderByArticle;
	}

	public void setOrderByArticle(String orderByArticle) {
		this.orderByArticle = orderByArticle;
	}

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

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
