package org.meruvian.yama.webapp;

import javax.inject.Inject;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.meruvian.inca.struts2.rest.annotation.Action;
import org.meruvian.inca.struts2.rest.annotation.Action.HttpMethod;
import org.meruvian.inca.struts2.rest.annotation.Result;
import org.meruvian.inca.struts2.rest.annotation.Results;
import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.news.action.NewsActionModel;
import org.meruvian.yama.news.service.NewsService;

import com.opensymphony.xwork2.ModelDriven;

@Action(name = "/home/news/detail", method = HttpMethod.GET)
@Results({
	@Result(name = DefaultAction.SHOW, type = "freemarker", location = "/view/news/detail.ftl")})
public class NewsDetailAction extends DefaultAction implements
ModelDriven<NewsActionModel> {
	
	private NewsActionModel model = new NewsActionModel();

	@Inject
	private NewsService newsService;

	@Action(name = "/{q}", method = HttpMethod.GET)
	@SkipValidation
	public String detail() {
		model.setNews(newsService.getNewsById(model.getQ()));

		return SHOW;
	}

	public NewsActionModel getModel() {
		return model;
	}

}

