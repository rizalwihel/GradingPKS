package com.example.grading.GradingPKS.controller;

import com.example.grading.GradingPKS.model.GradingResult;
import com.example.grading.GradingPKS.model.Supplier;
import com.example.grading.GradingPKS.service.GradingService;
import com.example.grading.GradingPKS.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/grading")
public class GradingController {

    @Autowired
    private GradingService gradingService;

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping
    public String showGradingForm(Model model) {
        model.addAttribute("gradingResult", new GradingResult());
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "grading-form";
    }

    @PostMapping("/calculate")
    public String calculateGrading(@ModelAttribute GradingResult gradingResult, Model model) {
        try {
            // Validasi input
            if (gradingResult.getBeratBuahMentah() == null || gradingResult.getBeratBuahMentah() < 0 ||
                gradingResult.getBeratBuahKurangMatang() == null || gradingResult.getBeratBuahKurangMatang() < 0 ||
                gradingResult.getBeratBuahMatang() == null || gradingResult.getBeratBuahMatang() < 0 ||
                gradingResult.getBeratBuahLewatMatang() == null || gradingResult.getBeratBuahLewatMatang() < 0) {
                throw new IllegalArgumentException("Semua berat buah harus diisi dan tidak boleh negatif");
            }
            
            if (gradingResult.getJumlahTandan() == null || gradingResult.getJumlahTandan() <= 0) {
                throw new IllegalArgumentException("Jumlah tandan harus lebih dari 0");
            }
            
            if (gradingResult.getHargaPerKg() == null || gradingResult.getHargaPerKg() <= 0) {
                throw new IllegalArgumentException("Harga per kg harus lebih dari 0");
            }
            
            gradingResult.setTanggalPengiriman(LocalDateTime.now());
            GradingResult result = gradingService.calculateGrading(gradingResult);
            model.addAttribute("result", result);
            return "grading-result";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error dalam perhitungan: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/grading-history")
    public String showHistory(Model model) {
        try {
            List<GradingResult> history = gradingService.getAllGradingResults();
            model.addAttribute("history", history);
            return "grading-history";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    // Endpoint untuk mengelola supplier
    @GetMapping("/supplier/add")
    public String showAddSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "supplier-form";
    }

    @PostMapping("/supplier/save")
    public String saveSupplier(@ModelAttribute Supplier supplier, Model model) {
        try {
            if (supplier.getNama() == null || supplier.getNama().trim().isEmpty()) {
                throw new IllegalArgumentException("Nama supplier harus diisi");
            }
            supplierRepository.save(supplier);
            return "redirect:/grading";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
} 