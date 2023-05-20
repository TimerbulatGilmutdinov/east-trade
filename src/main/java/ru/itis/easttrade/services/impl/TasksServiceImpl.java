package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.UpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.TasksRepository;
import ru.itis.easttrade.services.TasksService;
import ru.itis.easttrade.utils.RightsResolver;
import ru.itis.easttrade.utils.RoleChecker;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {
    private final TasksRepository tasksRepository;
    private final AccountsRepository accountsRepository;
    private final RoleChecker roleChecker;
    private final RightsResolver rightsResolver;

    @Override
    @Transactional
    public TaskDto saveTask(@ModelAttribute UpdateTaskDto taskDto, Authentication authentication) {
        String email = authentication.getName();
        Account account = accountsRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Account with email <" + email + "> not found"));
        Task newTask = Task.builder()
                .name(taskDto.getName())
                .price(taskDto.getPrice())
                .description(taskDto.getDescription())
                .state(Task.TaskState.PENDING)
                .publishDate(new Date())
                .topic(taskDto.getTopic())
                .account(account)
                .build();
        Task task = tasksRepository.save(newTask);
        return TaskDto.from(task);
    }

    @Override
    @Transactional
    public void updateTask(Integer id, @ModelAttribute UpdateTaskDto taskDto, Authentication authentication) {
        tasksRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with id <" + id + "> not found"));
        if (rightsResolver.resolveTaskAction(id, authentication)) {
            String newName = Jsoup.clean(taskDto.getName(), Safelist.basic());
            String newDescription = Jsoup.clean(taskDto.getDescription(), Safelist.basic());
            Task.TaskState newState = taskDto.getState();
            tasksRepository.updateById(id, newName, newDescription, newState);
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @Override
    @Transactional
    public void deleteTaskById(Integer id, Authentication authentication) {
        tasksRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with id <" + id + "> not found"));
        if (rightsResolver.resolveTaskAction(id, authentication)) {
            tasksRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("You don't have rights to do that");
        }
    }

    @Override
    public TaskDto getTaskById(Integer id) {
        Optional<Task> taskDB = tasksRepository.findById(id);
        Task task = taskDB.orElseThrow(() -> new NotFoundException("Task with id <" + id + "> not found"));
        return TaskDto.from(task);
    }

    @Override
    public List<TaskDto> getTasksByTopic(Topic topic) {
        return TaskDto.from(tasksRepository.findAllByTopic(topic));
    }

    @Override
    public List<TaskDto> getTasksByAccount(@ModelAttribute AccountDto accountDto) {
        Account account = accountsRepository.findByEmail(accountDto.getEmail()).orElseThrow(() -> new NotFoundException("Account with email <" + accountDto.getEmail() + "> not found"));
        return TaskDto.from(tasksRepository.findAllByAccount(account));
    }

    @Override
    public List<TaskDto> getAllArticles() {
        return TaskDto.from(tasksRepository.findAll());
    }

    @Override
    public List<TaskDto> getAllTasksOrderByDateAsc() {
        return TaskDto.from(tasksRepository.findAllByOrderByPublishDateAsc());
    }

    @Override
    public List<TaskDto> getAllTasksOrderByDateDesc() {
        return TaskDto.from(tasksRepository.findAllByOrderByPublishDateDesc());
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return TaskDto.from(tasksRepository.findAll());
    }

    @Override
    public List<TaskDto> getSortedTasksByTopic(List<TaskDto> tasks, Topic topic) {
        List<TaskDto> list = tasks.stream().filter(e -> e.getTopic().equals(topic)).collect(Collectors.toList());
        return list;
    }
}
