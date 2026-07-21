package com.craigwoodcock.fishingapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice(basePackages = "com.craigwoodcock.fishingapp.controller.webController")
public class GlobalWebExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFound(NoHandlerFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", "We don't seem to have a page there!");
        return modelAndView;
    }

    /**
     * Handles an invalid catch weight by redirecting back to whichever form
     * the user submitted from (new-catch or edit-catch), with the validation
     * message carried as a flash attribute rather than rendering a full error
     * page — this is a recoverable input mistake, not a system error.
     */
    @ExceptionHandler(InvalidWeightException.class)
    public ModelAndView handleInvalidWeight(InvalidWeightException ex, HttpServletRequest request,
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:" + resolveCatchFormUrl(request));
    }

    /**
     * Works out which catch form to redirect back to based on the request
     * path: a URI ending in a catch id (an update) goes back to its edit
     * form, otherwise (a create) it goes back to the new-catch form.
     */
    private String resolveCatchFormUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.matches(".*/catches/\\d+$") ? uri + "/edit" : uri + "/new";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView handleNoResourceFound(NoResourceFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", "Page not found");
        return modelAndView;
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ModelAndView handleSessionNotFound(SessionNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(CatchNotFoundException.class)
    public ModelAndView handleCatchNotFound(CatchNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(AnglerNotFoundException.class)
    public ModelAndView handleAnglerNotFound(AnglerNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

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
