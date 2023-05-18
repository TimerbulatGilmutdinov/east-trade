package ru.itis.easttrade.services;

import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.UpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.models.Topic;

import java.security.Principal;
import java.util.List;

public interface TasksService {
    TaskDto saveTask(UpdateTaskDto taskDto, Principal principal);
    void updateTask(Integer id, UpdateTaskDto updateTask);
    void deleteTaskById(Integer id);
    TaskDto getTaskById(Integer id);
    List<TaskDto> getAllArticles();
    List<TaskDto> getAllTasksOrderByDateAsc();
    List<TaskDto> getAllTasksOrderByDateDesc();
    List<TaskDto> getTasksByTopic(Topic topic);
    List<TaskDto> getTasksByAccount(AccountDto accountDto);
}
