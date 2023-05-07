package ru.itis.easttrade.aspects;


import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ValidationExceptionHandler {
//    @ExceptionHandler(ModelAttributeValidationException.class)
//    public ModelAndView handleRegistrationValidationException(@ModelAttribute NewOrUpdateAccountDto accountDto, ModelAttributeValidationException ex){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("registration");
//        modelAndView.addObject("accountDto", accountDto);
//        modelAndView.addObject("errorMessage", ex.getMessage());
//        return modelAndView;
//    }
}
