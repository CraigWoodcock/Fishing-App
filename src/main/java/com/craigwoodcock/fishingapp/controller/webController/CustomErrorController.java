package com.craigwoodcock.fishingapp.controller.webController;

import com.craigwoodcock.fishingapp.utils.ErrorRedirectResolver;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles both routes that error pages are reached by in this app:
 * /error/403, forwarded to directly by SecurityConfig's
 * accessDeniedPage, and /error, Spring Boot's default dispatch
 * target for any failure that happens outside a controller method's
 * own execution — such as a missing Thymeleaf template during view
 * rendering, which GlobalWebExceptionHandler cannot catch since its
 * handler method has already returned successfully by that point.
 */
@Controller
public class CustomErrorController implements ErrorController {

    private final ErrorRedirectResolver errorRedirectResolver;

    public CustomErrorController(ErrorRedirectResolver errorRedirectResolver) {
        this.errorRedirectResolver = errorRedirectResolver;
    }

    @GetMapping("/error/403")
    public String accessDenied(Model model) {
        model.addAttribute("message", "You do not have permission to access this page.");
        model.addAttribute("homeUrl", errorRedirectResolver.resolveHomeUrl());
        model.addAttribute("homeLabel", errorRedirectResolver.resolveHomeLabel());
        return "error/403";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusAttribute = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = (statusAttribute != null) ? Integer.parseInt(statusAttribute.toString()) : 500;

        model.addAttribute("homeUrl", errorRedirectResolver.resolveHomeUrl());
        model.addAttribute("homeLabel", errorRedirectResolver.resolveHomeLabel());

        if (statusCode == 404) {
            model.addAttribute("message", "We don't seem to have a page there!");
            return "error/404";
        }
        if (statusCode == 401) {
            model.addAttribute("message", "You need to log in to access this page.");
            return "error/401";
        }

        model.addAttribute("message", "An unexpected error occurred");
        return "error/500";
    }
    
}