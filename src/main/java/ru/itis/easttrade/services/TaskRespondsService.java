package ru.itis.easttrade.services;

import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.TaskRespond;

import java.util.List;

public interface TaskRespondsService {
    void addTaskRespond(Integer taskId, Integer accountId);

    void removeTaskRespond(Integer taskId, TaskRespond taskRespond);
    List<TaskRespond> getAllRespondsByTasksId(Integer id);
}
