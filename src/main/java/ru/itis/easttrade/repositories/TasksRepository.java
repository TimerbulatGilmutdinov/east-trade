package ru.itis.easttrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Article;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;

import java.util.Date;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByTopic(Topic topic);
    List<Task> findAllByPublishDate(Date date);
    List<Task> findAllByAccount(Account account);
    List<Task> findAllByOrderByPublishDateDesc();
    List<Task> findAllByOrderByPublishDateAsc();
    @Query(value = "select * from tasks a where a.topic=(select topic from tasks group by topic order by count(*) desc limit 1)", nativeQuery = true)
    List<Task> findAllByMostPopularTopic();
    @Query("from Task a where a.id = (select max(price) from Task)")
    Task findMostExpensive();
    @Modifying
    @Query("UPDATE Task a SET a.name = :newName, a.description= :newDescription, a.state = :newState WHERE a.id = :id")
    void updateById(@Param("id") Integer id, @Param("newName") String newName, @Param("newDescription") String newDescription, @Param("newState") Task.TaskState state);
}
