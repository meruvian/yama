package org.meruvian.yama.webapi.service.news;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.meruvian.yama.tot.news.News;

@Path("/api/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface NewsService {
	@GET
	@Path("/{id}")
	News getNewsById(@PathParam("id") long id);
	
	@GET
	List<News> findNewsByTitle(@QueryParam("title") @DefaultValue("") String title);
	
	@POST
	News saveNews(News news);
	
	@PUT
	@Path("/{id}")
	News updateNews(@PathParam("id") long id, News news);
	
	@DELETE
	@Path("/{id}")
	void deleteNews(@PathParam("id") long id);
}
