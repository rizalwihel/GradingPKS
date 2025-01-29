package com.example.grading.GradingPKS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceController {
    
    @GetMapping("/images/**")
    public String handleImages() {
        return "forward:/static/images/";
    }
} 