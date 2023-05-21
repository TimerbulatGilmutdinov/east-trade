package ru.itis.easttrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticlesRepository extends JpaRepository<Article, Integer> {
    Optional<Article> findByTitle(String title);

    List<Article> findAllByAccount(Account account);
    List<Article> findAllByOrderByPublishDateDesc();
    List<Article> findAllByOrderByPublishDateAsc();
    @Query(value = "SELECT * FROM articles WHERE date(publish_date) = CURRENT_DATE",nativeQuery = true)
    List<Article> findAllForToday();
    @Modifying
    @Query("UPDATE Article a SET a.title = :newTitle, a.content= :newContent WHERE a.id = :id")
    void updateById(@Param("id") Integer id, @Param("newTitle") String newTitle, @Param("newContent") String content);
}
