package com.craigwoodcock.fishingapp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = "com.craigwoodcock.fishingapp.webController")
public class GlobalWebExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ModelAndView handleUnauthorized(UserUnauthorizedException ex) {
        ModelAndView modelAndView = new ModelAndView("error/401");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UserForbiddenException.class)
    public ModelAndView handleForbidden(UserForbiddenException ex) {
        ModelAndView modelAndView = new ModelAndView("error/403");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericError(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("message", "An unexpected error occurred");
        return modelAndView;
    }

}
