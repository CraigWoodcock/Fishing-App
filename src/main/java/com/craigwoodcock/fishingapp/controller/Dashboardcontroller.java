package com.craigwoodcock.fishingapp.controller;

import com.craigwoodcock.fishingapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class Dashboardcontroller {

    @GetMapping()
    public String getDashboardScreen() {
        return "/dashboard";
    }
}
