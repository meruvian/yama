package org.meruvian.yama.article.action;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.article.service.ArticleService;
import org.meruvian.yama.news.action.NewsActionModel;
import org.meruvian.yama.news.service.NewsService;

import com.opensymphony.xwork2.ModelDriven;

@Action(name = "/backend/article/detail", method = HttpMethod.GET)
@Results({
	@Result(name = DefaultAction.SHOW, type = "freemarker", location = "/view/article/detail.ftl")})
public class ArticleDetailAction extends DefaultAction implements
ModelDriven<ArticleActionModel> {
	
	private ArticleActionModel model = new ArticleActionModel();

	@Inject
	private ArticleService articleService;

	@Action(name = "/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setArticle(articleService.getArticleById(model.getQ()));

		return SHOW;
	}

	public ArticleActionModel getModel() {
		return model;
	}

}
