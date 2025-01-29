package com.example.grading.GradingPKS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {

    @GetMapping("/grading/history")
    public String history() {
        return "history"; // pastikan ada file history.html di templates
    }
} 