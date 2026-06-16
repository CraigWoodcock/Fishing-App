package com.craigwoodcock.fishingapp.controller.webController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("'api")
public class CaptureController {

    @GetMapping("/captures/add")
    public static void message() {
        System.out.print("this is a test endpoint");
    }
}