package ru.itis.easttrade.services;

import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.models.Topic;

import java.security.Principal;
import java.util.List;

public interface TasksService {
    TaskDto saveTask(NewOrUpdateTaskDto taskDto, Principal principal);
    void updateTask(Integer id, NewOrUpdateTaskDto updateTask);
    void deleteTaskById(Integer id);
    TaskDto getTaskById(Integer id);
    List<TaskDto> getTasksByTopic(Topic topic);
    List<TaskDto> getTasksByAccount(AccountDto accountDto);
}
