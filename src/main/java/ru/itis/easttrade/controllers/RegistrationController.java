package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.itis.easttrade.dto.NewOrUpdateAccountDto;
import ru.itis.easttrade.exceptions.ModelAttributeValidationException;
import ru.itis.easttrade.services.AccountsService;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final AccountsService accountsService;

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("account", new NewOrUpdateAccountDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("account") NewOrUpdateAccountDto accountDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("account",accountDto);
            throw new ModelAttributeValidationException("Validation exception");
        }
        accountsService.addAccount(accountDto);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("LC#getLogin").build();
    }
}
