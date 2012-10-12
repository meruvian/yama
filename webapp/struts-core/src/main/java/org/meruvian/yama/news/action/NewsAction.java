package org.meruvian.yama.news.action;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.news.service.NewsService;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Action(name = "/backend/news")
@Results({
		@Result(name = DefaultAction.SUCCESS, type = "redirect", location = "/backend/news?success"),
		@Result(name = DefaultAction.INPUT, type = "freemarker", location = "/view/news/form.ftl"),
		@Result(name = DefaultAction.INDEX, type = "freemarker", location = "/view/news/list.ftl") })
public class NewsAction extends DefaultAction implements
		ModelDriven<NewsActionModel> {

	private NewsActionModel model = new NewsActionModel();

	@Inject
	private NewsService newsService;

	@SkipValidation
	@Action(name = "add", method = HttpMethod.GET)
	public String form() {

		return INPUT;
	}

	@Action(name = "add", method = HttpMethod.POST)
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "news.title", message = "Title cannot be blank", trim = true),
			@RequiredStringValidator(fieldName = "news.content", message = " Content cannot be blank", trim = true) })
	public String save() {

		newsService.saveNews(model.getNews());

		return SUCCESS;
	}

	@Action
	@SkipValidation
	public String execute() {
		model.setNewses(newsService.findNews(model.getQ(), model.getMax(),
				null, "ASC", model.getPage() - 1));
		return INDEX;
	}

	@Action(name = "edit/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String edit() {
		model.setNews(newsService.getNewsById(model.getQ()));

		return INPUT;
	}	

	@Action(name = "delete/{news.id}", method = HttpMethod.POST)
	@SkipValidation
	public String delete() {
		newsService.deleteNews(model.getNews());

		return SUCCESS;
	}

	public NewsActionModel getModel() {
		return model;
	}

}
