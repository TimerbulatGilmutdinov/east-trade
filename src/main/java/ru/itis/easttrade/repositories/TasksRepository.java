package ru.itis.easttrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;

import java.util.Date;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByTopic(Topic topic);
    List<Task> findAllByPublishDate(Date date);
    List<Task> findAllByAccount(Account account);
    @Modifying
    @Query("UPDATE Task a SET a.name = :newName, a.description= :newDescription WHERE a.id = :id")
    void updateById(@Param("id") Integer id, @Param("newName") String newName, @Param("newDescription") String newDescription);
}
