package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/4xx")
    public String handle4xx() {
        // resources/templates/error/4xx.html にフォワード
        System.out.println("ErrorController");
        return "error/4xx";
    }
}