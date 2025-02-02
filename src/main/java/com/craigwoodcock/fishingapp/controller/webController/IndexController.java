package com.craigwoodcock.fishingapp.controller.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller handling the application's landing page.
 *
 * @author Craig Woodcock
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * Displays the application's landing page.
     *
     * @return The index page view name
     */
    @GetMapping()
    public String getIndexPage() {
        return "index";
    }
}
