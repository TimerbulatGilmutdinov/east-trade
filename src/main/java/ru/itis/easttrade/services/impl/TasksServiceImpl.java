package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.easttrade.dto.NewOrUpdateAccountDto;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;
import ru.itis.easttrade.repositories.TasksRepository;
import ru.itis.easttrade.services.TasksService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {
    private final TasksRepository tasksRepository;
    @Override
    public TaskDto saveTask(NewOrUpdateTaskDto taskDto) {
//        Task task = tasksRepository.findBy
        return null;
    }

    @Override
    public TaskDto updateTask(Integer id, NewOrUpdateTaskDto taskDto) {
        return null;
    }

    @Override
    public void deleteTaskById(Integer id) {

    }

    @Override
    public TaskDto getTaskById(Integer id) {
        return null;
    }

    @Override
    public List<TaskDto> getTasksByTopic(Topic topic) {
        return null;
    }

    @Override
    public List<TaskDto> getTasksByAccount() {
        return null;
    }
}
