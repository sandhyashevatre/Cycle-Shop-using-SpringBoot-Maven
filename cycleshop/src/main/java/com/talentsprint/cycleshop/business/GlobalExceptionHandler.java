package com.talentsprint.cycleshop.business;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import com.talentsprint.cycleshop.exception.NotLoggedInException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ModelAndView handleNotLoggedInException() {
        // Redirect to the login page
        return new ModelAndView("redirect:/loginpage");
    }
}
