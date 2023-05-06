package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.easttrade.repositories.AccountsRepository;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final AccountsRepository accountsRepository;
    @GetMapping("/")
    private String getMainPage(Model model){
        return "welcome";
    }
}
