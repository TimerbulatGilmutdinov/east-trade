package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.services.TasksService;

@Controller
@RequiredArgsConstructor
public class TasksController {
    private final TasksService tasksService;

    @GetMapping("/tasks/{id}")
    public String getTask(@PathVariable("id") Integer id, Model model){
        TaskDto task = tasksService.getTaskById(id);
        model.addAttribute("task", task);
        return "task";
    }
}
