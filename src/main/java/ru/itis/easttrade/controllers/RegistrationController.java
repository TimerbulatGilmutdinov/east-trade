package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.NewOrUpdateAccountDto;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.services.AccountsService;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final AccountsService accountsService;

    @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("accountForm", new Account());
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(Model model, BindingResult bindingResult, NewOrUpdateAccountDto accountDto){
        accountsService.addAccount(accountDto);
        return "redirect:"+ MvcUriComponentsBuilder.fromMappingName("").build();
    }
}
