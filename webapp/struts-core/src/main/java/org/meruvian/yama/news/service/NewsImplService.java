package org.meruvian.yama.news.service;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.meruvian.yama.news.News;
import org.meruvian.yama.news.dao.NewsDomain;
import org.meruvian.yama.persistence.EntityListWrapper;
import org.meruvian.yama.persistence.LogInformation;
import org.meruvian.yama.persistence.LogInformation.StatusFlag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NewsImplService implements NewsService {
	
	@Inject
	private NewsDomain newsDomain;

	@Transactional
	public News saveNews(News news) {
		if (StringUtils.isBlank(news.getId())) {

			news.setId(null);
			news.setLogInformation(new LogInformation());

			newsDomain.persist(news);
		} else {
			News n = newsDomain.load(news.getId());
			n.setUser(news.getUser());
			n.setTitle(news.getTitle());
			n.setContent(news.getContent());

			n.getLogInformation().setUpdateDate(new Date());

			return n;
		}
		return news;
	}

	@Override
	public News getNewsById(String id) {
		return newsDomain.findById(id);
	}

	@Transactional
	public void deleteNews(News news) {
		news = newsDomain.load(news.getId());
		news.getLogInformation().setStatusFlag(StatusFlag.INACTIVE);
		
	}

	@Override
	public EntityListWrapper<News> findNews(String title, String orderby,
			String order, int max, int page) {
		return newsDomain.findNews(title, orderby, order, max, page, "AND");
	}

	@Override
	public EntityListWrapper<News> findNews(String field, int max,
			String orderBy, String order, int page) {
		return newsDomain.findNews( field, orderBy,
				order, max, page, "OR");
	}

}
