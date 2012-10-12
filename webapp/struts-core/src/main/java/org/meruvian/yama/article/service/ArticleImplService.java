package org.meruvian.yama.article.service;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.article.Article;
import org.meruvian.yama.article.dao.ArticleDomain;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ArticleImplService implements ArticleService{
	
	@Inject
	private ArticleDomain articleDomain;

	@Transactional
	public Article saveArticle(Article article) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(article.getId())) {

			article.setId(null);
			article.setLogInformation(new LogInformation());

			articleDomain.persist(article);
		} else {
			Article a = articleDomain.load(article.getId());
			a.setUser(article.getUser());
			a.setTitle(article.getTitle());
			a.setContent(article.getContent());

			a.getLogInformation().setUpdateDate(new Date());

			return a;
		}
		return article;
	}

	@Override
	public Article getArticleById(String id) {
		return articleDomain.findById(id);
	}

	@Transactional
	public void deleteArticle(Article article) {
		article = articleDomain.load(article.getId());
		article.getLogInformation().setStatusFlag(StatusFlag.INACTIVE);		
	}

	@Override
	public EntityListWrapper<Article> findArticles(String title,
			String orderby, String order, int max, int page) {
		// TODO Auto-generated method stub
		return articleDomain.findArticles(title, orderby, order, max, page, "AND");
	}

	@Override
	public EntityListWrapper<Article> findArticles(String field, int max,
			String orderBy, String order, int page) {
		// TODO Auto-generated method stub
		return articleDomain.findArticles(field, orderBy,	order, max, page, "OR");
	}

}
