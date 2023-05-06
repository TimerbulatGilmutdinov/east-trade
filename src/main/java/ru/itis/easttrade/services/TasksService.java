package ru.itis.easttrade.services;

import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.NewOrUpdateAccountDto;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.models.Topic;

import java.util.List;

public interface TasksService {
    TaskDto saveTask(NewOrUpdateTaskDto taskDto);
    TaskDto updateTask(Integer id, NewOrUpdateTaskDto updateTask);
    void deleteTaskById(Integer id);
    TaskDto getTaskById(Integer id);
    List<TaskDto> getTasksByTopic(Topic topic);
    List<TaskDto> getTasksByAccount();
}
