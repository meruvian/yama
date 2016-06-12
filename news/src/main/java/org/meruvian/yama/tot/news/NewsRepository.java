package org.meruvian.yama.tot.news;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<News, Long> {
	News findById(long id);
	
	@Query("SELECT n FROM News n WHERE n.title LIKE %?1%")
	List<News> findByTitle(String title);
}
