package com.example.grading.GradingPKS.controller;

import com.example.grading.GradingPKS.model.GradingResult;
import com.example.grading.GradingPKS.model.User;
import com.example.grading.GradingPKS.service.GradingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private GradingService gradingService;

    @GetMapping
    public String showDashboard(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/";
            }

            // Inisialisasi nilai default
            model.addAttribute("user", user);
            model.addAttribute("recentGradings", new ArrayList<>());
            model.addAttribute("totalPembayaran", 0.0);
            model.addAttribute("totalDenda", 0.0);
            model.addAttribute("totalTransaksi", 0L);
            model.addAttribute("rataRataBerat", 0.0);

            // Ambil data
            try {
                model.addAttribute("recentGradings", gradingService.getRecentGradings());
                model.addAttribute("totalPembayaran", gradingService.getTotalPembayaranHariIni());
                model.addAttribute("totalDenda", gradingService.getTotalDendaHariIni());
                model.addAttribute("totalTransaksi", gradingService.getTotalTransaksiHariIni());
                model.addAttribute("rataRataBerat", gradingService.getRataRataBeratHariIni());
            } catch (Exception e) {
                // Log error but continue with default values
                e.printStackTrace();
            }

            return "dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Terjadi kesalahan saat memuat dashboard");
            return "error";
        }
    }
} 