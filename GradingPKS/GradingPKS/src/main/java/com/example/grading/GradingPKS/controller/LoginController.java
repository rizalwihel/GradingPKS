package com.example.grading.GradingPKS.controller;

import com.example.grading.GradingPKS.model.User;
import com.example.grading.GradingPKS.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, 
                       @RequestParam("password") String password, 
                       HttpSession session,
                       Model model) {
        try {
            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                return "redirect:/dashboard";
            }
            model.addAttribute("error", "Username atau password salah");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Terjadi kesalahan saat login");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            userService.register(user);
            model.addAttribute("success", "Registrasi berhasil, silakan login");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
} 