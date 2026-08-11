package com.craigwoodcock.fishingapp.exception;

import com.craigwoodcock.fishingapp.utils.ErrorRedirectResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice(basePackages = "com.craigwoodcock.fishingapp.controller.webController")
public class GlobalWebExceptionHandler {

    private final ErrorRedirectResolver errorRedirectResolver;

    public GlobalWebExceptionHandler(ErrorRedirectResolver errorRedirectResolver) {
        this.errorRedirectResolver = errorRedirectResolver;
    }

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

    /**
     * Handles a catch time set before the session's start date by redirecting
     * back to whichever form the user submitted from, with the validation
     * message carried as a flash attribute.
     */
    @ExceptionHandler(InvalidCatchTimeException.class)
    public ModelAndView handleInvalidCatchTime(InvalidCatchTimeException ex, HttpServletRequest request,
                                               RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:" + resolveCatchFormUrl(request));
    }

    @ExceptionHandler(AnglerNotFoundException.class)
    public ModelAndView handleAnglerNotFound(AnglerNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(AnglerAlreadyExistsException.class)
    public ModelAndView handleAnglerAlreadyExistsWeb(AnglerAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/admin/anglers");
    }

    @ExceptionHandler(AnglerHasRecordsException.class)
    public ModelAndView handleAnglerHasRecordsWeb(AnglerHasRecordsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/admin/anglers");
    }

    @ExceptionHandler(AnglerLinkedToUserException.class)
    public ModelAndView handleAnglerLinkedToUser(AnglerLinkedToUserException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/admin/anglers");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ModelAndView handleUserAlreadyExistsWeb(UserAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/account");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ModelAndView handleInvalidCredentialsWeb(InvalidCredentialsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:/account");
    }

    /**
     * Handles an angler being added without an email by redirecting back to
     * the add-angler form with the validation message as a flash attribute —
     * a recoverable input mistake, not a system error.
     */
    @ExceptionHandler(MissingAnglerEmailException.class)
    public ModelAndView handleMissingAnglerEmail(MissingAnglerEmailException ex, HttpServletRequest request,
                                                 RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return new ModelAndView("redirect:" + request.getRequestURI() + "/new");
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

    /**
     * Handles a user attempting to view, edit, or delete a session or catch
     * they don't have permission for. This is needed alongside
     * SecurityConfig's accessDeniedPage because an AccessDeniedException
     * thrown inside a controller method (e.g. requireOwnership checks) is
     * intercepted by this class's own Exception catch-all before it can ever
     * propagate out to Spring Security's ExceptionTranslationFilter — so
     * without this handler it would fall through to error/500 instead of
     * error/403. Uses buildErrorView so this renders identically to
     * CustomErrorController's accessDenied(), which handles the same view
     * for 403s that occur outside a controller (e.g. role-based rejection).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException ex) {
        return buildErrorView("error/403", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericError(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error/500");
        modelAndView.addObject("message", "An unexpected error occurred");
        return modelAndView;
    }

    /**
     * Builds an error ModelAndView with the given message, plus a
     * homeUrl/homeLabel pair the error templates use for their
     * "back" button. This is resolved from the security context
     * rather than the request path, since some error views (like
     * 403, via SecurityConfig's accessDeniedPage) are reached by an
     * internal forward rather than a fresh request — the security
     * context stays valid across that forward, the request path
     * doesn't reliably tell us who's looking at the page.
     */
    private ModelAndView buildErrorView(String viewName, String message) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("message", message);
        modelAndView.addObject("homeUrl", errorRedirectResolver.resolveHomeUrl());
        modelAndView.addObject("homeLabel", errorRedirectResolver.resolveHomeLabel());
        return modelAndView;
    }

}
