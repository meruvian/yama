package org.meruvian.yama.article.service;

import org.meruvian.yama.article.Article;
import org.meruvian.yama.persistence.EntityListWrapper;

public interface ArticleService {
	
	Article saveArticle(Article article);

	Article getArticleById(String id);
	
	void deleteArticle(Article article);

	EntityListWrapper<Article> findArticles(String title, String orderby,
			String order, int max, int page);

	EntityListWrapper<Article> findArticles(String field, int max, String orderBy,
			String order, int page);

}
