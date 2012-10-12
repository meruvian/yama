package org.meruvian.yama.article.action;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.article.service.ArticleService;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Action(name = "/backend/article")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/article?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/article/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/article/list.ftl") })
public class ArticleAction extends DefaultAction implements
		ModelDriven<ArticleActionModel> {

	private ArticleActionModel model = new ArticleActionModel();

	@Inject
	private ArticleService articleService;

	@SkipValidation
	@Action(name = "add", method = HttpMethod.GET)
	public String form() {

		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "article.title", message = "Title cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "article.content", message = " Content cannot be blank", trim = true) })
	public String save() {

		articleService.saveArticle(model.getArticle());

		return SUCCESS;
	}

	@Action
	@SkipValidation
	public String execute() {
		model.setArticles(articleService.findArticles(model.getQ(), model.getMax(),
				null, "ASC", model.getPage() - 1));
		return INDEX;
	}

	@Action(name = "edit/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setArticle(articleService.getArticleById(model.getQ()));

		return INPUT;
	}

	@Action(name = "delete/{article.id}", method = HttpMethod.POST)
	@SkipValidation
	public String delete() {
		articleService.deleteArticle(model.getArticle());

		return SUCCESS;
	}

	public ArticleActionModel getModel() {
		return model;
	}

}
