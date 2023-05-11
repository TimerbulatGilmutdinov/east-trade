package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.NewOrUpdateTaskDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.TasksService;

@Controller
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;
    private final AccountsService accountsService;

    @GetMapping("/tasks/{id}")
    public String getTask(@PathVariable("id") Integer id, Model model){
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
    public String createTask(@ModelAttribute("task") NewOrUpdateTaskDto taskDto){
        tasksService.saveTask(taskDto);
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("TC#getTask").arg(0,taskDto.getId()).build();
    }
}
