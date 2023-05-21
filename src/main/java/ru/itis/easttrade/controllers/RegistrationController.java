package ru.itis.easttrade.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.easttrade.dto.NewOrUpdateAccountDto;
import ru.itis.easttrade.services.AccountsService;

import javax.validation.Valid;

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
    public String postRegistration(@Valid @ModelAttribute("account") NewOrUpdateAccountDto accountDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(FieldError error:bindingResult.getFieldErrors()){
                stringBuilder.append(error.getDefaultMessage()).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
            redirectAttributes.addFlashAttribute("validationErrors", stringBuilder.toString());
            model.addAttribute("account",accountDto);
            return "redirect:"+"/registration";
        }
        accountsService.addAccount(accountDto);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("LC#getLogin").build();
    }
}
