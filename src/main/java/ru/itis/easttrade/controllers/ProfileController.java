package ru.itis.easttrade.controllers;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.repositories.TasksRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    @Autowired
    private final TasksRepository tasksRepository;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        return "profile";
    }

    @GetMapping("/my-tasks")
    public String myTasks(HttpServletRequest request, Model model) {
        Account account = (Account) request.getAttribute("account");
        List<Task> tasks = tasksRepository.findAllByAccount(account);
        model.addAttribute("tasks", tasks);
        return "my-tasks";
    }
}
