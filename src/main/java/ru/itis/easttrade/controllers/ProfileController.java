package ru.itis.easttrade.controllers;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.repositories.TasksRepository;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.ArticlesService;
import ru.itis.easttrade.services.TasksService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final TasksService tasksService;
    private final AccountsService accountsService;
    private final ArticlesService articlesService;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        AccountDto accountDto = accountsService.getAccountByEmail(principal.getName());
        List<TaskDto> tasks = tasksService.getTasksByAccount(accountDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("tasks", tasks);
        model.addAttribute("articles", articlesService.getArticlesByAccount(accountDto));
        return "profile";
    }
}
