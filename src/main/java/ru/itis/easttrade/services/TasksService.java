package ru.itis.easttrade.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.UpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.models.Topic;

import java.util.List;

public interface TasksService {
    TaskDto saveTask(UpdateTaskDto taskDto, Authentication authentication);
    void updateTask(Integer id, UpdateTaskDto updateTask, Authentication authentication);
    ResponseEntity<String> deleteTaskById(Integer id, Authentication authentication);
    TaskDto getTaskById(Integer id);
    List<TaskDto> getAllArticles();
    List<TaskDto> getAllTasksOrderByDateAsc();
    List<TaskDto> getAllTasksOrderByDateDesc();
    List<TaskDto> getTasksByTopic(Topic topic);
    List<TaskDto> getTasksByAccount(AccountDto accountDto);
    List<TaskDto> getAllTasks();
    List<TaskDto> getSortedTasksByTopic(List<TaskDto> list, Topic topic);
    List<TaskDto> getAllTasksForToday();
}
