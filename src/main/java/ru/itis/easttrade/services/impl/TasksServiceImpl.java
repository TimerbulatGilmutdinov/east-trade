package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.TasksRepository;
import ru.itis.easttrade.services.TasksService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {
    private final TasksRepository tasksRepository;
    private final AccountsRepository accountsRepository;
    @Override
    public TaskDto saveTask(@ModelAttribute NewOrUpdateTaskDto taskDto) {
//        Task task = tasksRepository.findBy
        return null;
    }

    @Override
    public TaskDto updateTask(Integer id,@ModelAttribute NewOrUpdateTaskDto taskDto) {
        return null;
    }

    @Override
    public void deleteTaskById(Integer id) {

    }

    @Override
    public TaskDto getTaskById(Integer id) {
        Optional<Task> taskDB = tasksRepository.findById(id);
        Task task = taskDB.orElseThrow(()->new NotFoundException("Task with id <"+id+"> not found"));
        return TaskDto.from(task);
    }

    @Override
    public List<TaskDto> getTasksByTopic(Topic topic) {
        return TaskDto.from(tasksRepository.findAllByTopic(topic));
    }

    @Override
    public List<TaskDto> getTasksByAccount(AccountDto accountDto) {
        Account account = accountsRepository.findByEmail(accountDto.getEmail()).get();
        return TaskDto.from(tasksRepository.findAllByAccount(account));
    }
}
