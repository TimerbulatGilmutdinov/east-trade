package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.repositories.AccountsRepository;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final AccountsRepository accountsRepository;
    @GetMapping("/welcome")
    public String getWelcomePage(Model model){
        return "welcome";
    }
    @GetMapping("/")
    public String redirectToWelcomePage(Model model){
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("WC#getWelcomePage").build();
    }
}
