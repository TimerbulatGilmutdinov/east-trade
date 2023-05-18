package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.UpdateTaskDto;
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
        model.addAttribute("task", new UpdateTaskDto());
        return "create-task";
    }

    @PostMapping("/create-task")
    public String createTask(@ModelAttribute("task") UpdateTaskDto taskDto, Principal principal){
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

    @PostMapping("/tasks/{id}/update")
    public String updateTask(@PathVariable("id") Integer id, @ModelAttribute UpdateTaskDto taskDto){
        tasksService.updateTask(id,taskDto);
        return "redirect:"+MvcUriComponentsBuilder.fromMappingName("TC#getTaskById").arg(0,id).build();
    }

    @GetMapping("/tasks")
    public String getAllTasks(@RequestParam(value = "sortBy",defaultValue = "new") String sort, Model model){
        List<TaskDto> tasks;
        if (sort.equals("new")) {
            tasks = tasksService.getAllTasksOrderByDateAsc();
        } else if (sort.equals("old")) {
            tasks = tasksService.getAllTasksOrderByDateDesc();
        } else {
            tasks = tasksService.getAllTasksOrderByDateDesc();
        }
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
}
