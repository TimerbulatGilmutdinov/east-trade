package ru.itis.easttrade.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.easttrade.models.TaskRespond;

import java.util.List;
import java.util.Optional;

public interface TaskRespondRepository extends JpaRepository<TaskRespond, Integer> {
    Optional<TaskRespond> findByAccountId(Integer id);
    List<TaskRespond> findByTasksId(Integer id);
}
