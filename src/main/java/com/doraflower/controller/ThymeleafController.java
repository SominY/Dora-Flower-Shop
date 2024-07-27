package com.doraflower.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/ex")
    public String thymeleafExample() {
        return "thymeleafEx";
    }
}
