package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.ArticlesService;
import ru.itis.easttrade.services.TasksService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;
    private final ArticlesService articlesService;
    private final TasksService tasksService;
    private final AccountsRepository accountsRepository;

    @GetMapping("/users/{id}")
    public String getAccountProfile(@PathVariable("id") Integer id, Model model){
        AccountDto accountDto = accountsService.getAccountById(id);
        model.addAttribute("account", accountDto);
        model.addAttribute("tasks", tasksService.getTasksByAccount(accountDto));
        model.addAttribute("articles", articlesService.getArticlesByAccount(accountDto));
        return "profile";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        return "users";
    }

    @GetMapping("/search")
    public String searchUsers(Model model) {
        return "search";
    }

    @GetMapping("/search/{name}")
    @ResponseBody
    public List<Account> getAccountByNameLike(@PathVariable("name")String name){
        return accountsRepository.findByNameContainingIgnoreCase(name);
    }
}
