package com.talentsprint.cycleshop.business;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.talentsprint.cycleshop.exception.NotLoggedInException;

@Aspect
@Component
public class LoginAspect {

    @Autowired
    private LoggedInUser loggedInUser;

    @Before("execution(* com.talentsprint.cycleshop.controller.CycleController.*(..))")
    public void checkLoggedInUser() {
        if (loggedInUser.getLoggedInUser() == null) {
            // Redirect to the login page
            throw new NotLoggedInException("throws a new exception"); // You need to create this exception class
        }
    }
}

