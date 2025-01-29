package com.example.grading.GradingPKS.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "grading_results")
public class GradingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    private LocalDateTime tanggalPengiriman;
    
    // Input berat dalam kg
    private Double beratBuahMentah;
    private Double beratBuahKurangMatang;
    private Double beratBuahMatang;
    private Double beratBuahLewatMatang;
    private Double beratTotalTBS;
    private Integer jumlahTandan;
    private Double beratPerTandan;
    
    // Persentase akan dihitung otomatis
    private Double persentaseBuahMentah;
    private Double persentaseBuahLewatMatang;
    private Double persentaseTandanKosong;
    private Double persentaseGagangPanjang;
    private Double persentaseBrondolan;
    private Double persentaseBrondolanKotor;
    
    // Hasil perhitungan denda
    private Double dendaBuahMentah;
    private Double dendaBuahLewatMatang;
    private Double dendaTandanKosong;
    private Double dendaGagangPanjang;
    private Double dendaBrondolan;
    private Double dendaBrondolanKotor;
    private Double dendaBeratMinimal;
    
    private Double hargaPerKg;
    private Double totalPembayaran;
    private Double totalDenda;
    private Double pembayaranBersih;

    // Constructor kosong diperlukan oleh JPA
    public GradingResult() {
        // Inisialisasi nilai default
        this.beratTotalTBS = 0.0;
        this.beratPerTandan = 0.0;
        this.persentaseBuahMentah = 0.0;
        this.persentaseBuahLewatMatang = 0.0;
        this.persentaseTandanKosong = 0.0;
        this.persentaseGagangPanjang = 0.0;
        this.persentaseBrondolan = 0.0;
        this.persentaseBrondolanKotor = 0.0;
        
        this.dendaBuahMentah = 0.0;
        this.dendaBuahLewatMatang = 0.0;
        this.dendaTandanKosong = 0.0;
        this.dendaGagangPanjang = 0.0;
        this.dendaBrondolan = 0.0;
        this.dendaBrondolanKotor = 0.0;
        this.dendaBeratMinimal = 0.0;
        
        this.totalPembayaran = 0.0;
        this.totalDenda = 0.0;
        this.pembayaranBersih = 0.0;
        
        this.beratBuahMentah = 0.0;
        this.beratBuahKurangMatang = 0.0;
        this.beratBuahMatang = 0.0;
        this.beratBuahLewatMatang = 0.0;
    }
} 