package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.repositories.AccountsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class WelcomeController {
    private final AccountsRepository accountsRepository;
    @GetMapping("/welcome")
    public String getWelcomePage(){
        return "welcome";
    }
    @GetMapping("/")
    public String redirectToWelcomePage(){
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("WC#getWelcomePage").build();
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:"+"/welcome";
    }
}
