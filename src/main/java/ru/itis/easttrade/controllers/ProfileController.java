package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.TaskDto;
import ru.itis.easttrade.services.AccountsService;
import ru.itis.easttrade.services.ArticlesService;
import ru.itis.easttrade.services.ProductsService;
import ru.itis.easttrade.services.TasksService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final TasksService tasksService;
    private final AccountsService accountsService;
    private final ArticlesService articlesService;
    private final ProductsService productsService;

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {
        AccountDto accountDto = accountsService.getAccountByEmail(authentication.getName());
        List<TaskDto> tasks = tasksService.getTasksByAccount(accountDto);
        model.addAttribute("account", accountDto);
        model.addAttribute("tasks", tasks);
        model.addAttribute("articles", articlesService.getArticlesByAccount(accountDto));
        model.addAttribute("products", productsService.getProductsByAccount(accountDto));
        return "profile";
    }
}
