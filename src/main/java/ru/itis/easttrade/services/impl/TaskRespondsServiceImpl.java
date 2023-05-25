package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.TaskRespond;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.TaskRespondRepository;
import ru.itis.easttrade.repositories.TasksRepository;
import ru.itis.easttrade.services.TaskRespondsService;
import ru.itis.easttrade.services.TasksService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskRespondsServiceImpl implements TaskRespondsService {
    private final TasksRepository tasksRepository;
    private final AccountsRepository accountsRepository;
    private final TasksService tasksService;
    private final TaskRespondRepository taskRespondRepository;

    @Override
    public List<TaskRespond> getAllRespondsByTasksId(Integer id){
        return taskRespondRepository.findByTasksId(id);
    }

    @Override
    public void addTaskRespond(Integer taskId, Integer accountId) {
        Task task = tasksRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task with id <" + taskId + "> not found"));
        Account account = accountsRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account with id <" + accountId + "> not found"));
        TaskRespond taskRespond = TaskRespond.builder()
                .account(account)
                .build();
        if (taskRespondRepository.findByTasksId(taskId).isEmpty()&&taskRespondRepository.findByAccountId(accountId).isEmpty()) {
            taskRespondRepository.save(taskRespond);
        }
    }

    @Override
    public void removeTaskRespond(Integer taskId, TaskRespond taskRespond) {
        Task task = tasksRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task with id <" + taskId + "> not found"));
        task.getTaskResponds().remove(taskRespond);
    }
}
