package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.TasksService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;
    private final AccountsService accountsService;

    @GetMapping("/tasks/{id}")
    public String getTaskById(@PathVariable("id") Integer id, Model model){
        TaskDto task = tasksService.getTaskById(id);
        model.addAttribute("task", task);
        return "task";
    }

    @GetMapping("/create-task")
    public String getCreateTask(Model model){
        model.addAttribute("task", new NewOrUpdateTaskDto());
        return "create-task";
    }

    @PostMapping("/create-task")
    public String createTask(@ModelAttribute("task") NewOrUpdateTaskDto taskDto, Principal principal){
        int id = tasksService.saveTask(taskDto, principal).getId();
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("TC#getTaskById").arg(0,id).build();
    }

    @GetMapping("/my-tasks")
    public String myTasks(Model model, Principal principal) {
        AccountDto accountDto = accountsService.getAccountByEmail(principal.getName());
        List<TaskDto> tasks = tasksService.getTasksByAccount(accountDto);
        model.addAttribute("tasks", tasks);
        return "my-tasks";
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable("id") Integer id){
        tasksService.deleteTaskById(id);
    }

    @GetMapping("/tasks/{id}/update")
    public String getUpdateTask(@PathVariable("id") Integer id, Model model){
        TaskDto task = tasksService.getTaskById(id);
        model.addAttribute("task", task);
        return "update-task";
    }
}
