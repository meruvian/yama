package org.meruvian.yama.news.service;

import org.meruvian.yama.news.News;
import org.meruvian.yama.persistence.EntityListWrapper;

public interface NewsService {

	News saveNews(News news);

	News getNewsById(String id);
	
	void deleteNews(News news);

	EntityListWrapper<News> findNews(String title, String orderby,
			String order, int max, int page);

	EntityListWrapper<News> findNews(String field, int max, String orderBy,
			String order, int page);
	
}
