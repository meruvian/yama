package org.meruvian.yama.webapp;

import javax.inject.Inject;

import org.meruvian.yama.actions.DefaultAction;
import org.meruvian.yama.article.service.ArticleService;
import org.meruvian.yama.news.service.NewsService;

import com.opensymphony.xwork2.ModelDriven;

public class MainAction extends DefaultAction implements ModelDriven<MainModel> {

	private static final long serialVersionUID = -4442714455678005985L;

	private MainModel model = new MainModel();

	@Inject
	private NewsService newsService;
	
	@Inject
	private ArticleService articleService;
	

	@Override
	public String execute() {
		
		model.setNewses(newsService.findNews(model.getQ(), model.getMaxNews(),
				null, "ASC", model.getPageNews() - 1));
		
		model.setArticles(articleService.findArticles(model.getQ(), model.getMaxArticle(),
				null, "ASC", model.getPageArticle() - 1));

		return SUCCESS;
	}

	public MainModel getModel() {
		return model;
	}


}
