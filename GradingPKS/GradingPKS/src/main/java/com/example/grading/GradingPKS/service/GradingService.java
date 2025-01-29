package com.example.grading.GradingPKS.service;

import com.example.grading.GradingPKS.model.GradingResult;
import com.example.grading.GradingPKS.repository.GradingResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class GradingService {
    
    @Autowired
    private GradingResultRepository gradingResultRepository;
    
    public GradingResult calculateGrading(GradingResult input) {
        try {
            // Inisialisasi semua nilai denda dan persentase dengan 0.0
            input.setDendaBuahMentah(0.0);
            input.setDendaBuahLewatMatang(0.0);
            input.setDendaTandanKosong(0.0);
            input.setDendaGagangPanjang(0.0);
            input.setDendaBrondolan(0.0);
            input.setDendaBrondolanKotor(0.0);
            input.setDendaBeratMinimal(0.0);
            
            // Inisialisasi persentase dengan 0.0
            input.setPersentaseTandanKosong(0.0);
            input.setPersentaseGagangPanjang(0.0);
            input.setPersentaseBrondolan(0.0);
            input.setPersentaseBrondolanKotor(0.0);
            
            // Hitung berat total
            double beratTotal = (input.getBeratBuahMentah() != null ? input.getBeratBuahMentah() : 0.0) + 
                              (input.getBeratBuahKurangMatang() != null ? input.getBeratBuahKurangMatang() : 0.0) + 
                              (input.getBeratBuahMatang() != null ? input.getBeratBuahMatang() : 0.0) + 
                              (input.getBeratBuahLewatMatang() != null ? input.getBeratBuahLewatMatang() : 0.0);
            input.setBeratTotalTBS(beratTotal);
            
            // Hitung berat per tandan
            if (input.getJumlahTandan() != null && input.getJumlahTandan() > 0) {
                input.setBeratPerTandan(beratTotal / input.getJumlahTandan());
            } else {
                input.setBeratPerTandan(0.0);
            }
            
            // Hitung persentase
            if (beratTotal > 0) {
                double beratBuahMentah = input.getBeratBuahMentah() != null ? input.getBeratBuahMentah() : 0.0;
                double beratBuahLewatMatang = input.getBeratBuahLewatMatang() != null ? input.getBeratBuahLewatMatang() : 0.0;
                
                input.setPersentaseBuahMentah((beratBuahMentah / beratTotal) * 100);
                input.setPersentaseBuahLewatMatang((beratBuahLewatMatang / beratTotal) * 100);
            }
            
            // Hitung denda buah mentah (50% x berat BM x harga)
            if (input.getBeratBuahMentah() != null && input.getBeratBuahMentah() > 0 && input.getHargaPerKg() != null) {
                input.setDendaBuahMentah(0.5 * input.getBeratBuahMentah() * input.getHargaPerKg());
            }
            
            // Hitung denda buah lewat matang (25% x berat BLM x harga)
            if (input.getBeratBuahLewatMatang() != null && input.getBeratBuahLewatMatang() > 0 && input.getHargaPerKg() != null) {
                input.setDendaBuahLewatMatang(0.25 * input.getBeratBuahLewatMatang() * input.getHargaPerKg());
            }
            
            // Hitung denda tandan kosong
            Double persentaseTandanKosong = input.getPersentaseTandanKosong();
            if (persentaseTandanKosong != null && persentaseTandanKosong > 0 && input.getHargaPerKg() != null) {
                input.setDendaTandanKosong(1.0 * (persentaseTandanKosong/100) * beratTotal * input.getHargaPerKg());
            }
            
            // Hitung denda gagang panjang
            Double persentaseGagangPanjang = input.getPersentaseGagangPanjang();
            if (persentaseGagangPanjang != null && persentaseGagangPanjang > 0 && input.getHargaPerKg() != null) {
                input.setDendaGagangPanjang(0.01 * (persentaseGagangPanjang/100) * beratTotal * input.getHargaPerKg());
            }
            
            // Hitung denda brondolan kurang
            Double persentaseBrondolan = input.getPersentaseBrondolan();
            if (persentaseBrondolan != null && persentaseBrondolan < 12.5 && input.getHargaPerKg() != null) {
                input.setDendaBrondolan(0.3 * (12.5 - persentaseBrondolan)/100 * beratTotal * input.getHargaPerKg());
            }
            
            // Hitung denda brondolan kotor
            Double persentaseBrondolanKotor = input.getPersentaseBrondolanKotor();
            if (persentaseBrondolanKotor != null && persentaseBrondolanKotor > 0 && input.getHargaPerKg() != null) {
                input.setDendaBrondolanKotor(2 * (persentaseBrondolanKotor/100) * beratTotal * input.getHargaPerKg());
            }
            
            // Hitung denda berat minimal
            if (input.getBeratPerTandan() != null && input.getBeratPerTandan() < 3.0 && input.getHargaPerKg() != null) {
                input.setDendaBeratMinimal(0.7 * beratTotal * input.getHargaPerKg());
            }
            
            // Hitung total pembayaran normal
            if (input.getHargaPerKg() != null) {
                input.setTotalPembayaran(beratTotal * input.getHargaPerKg());
            } else {
                input.setTotalPembayaran(0.0);
            }
            
            // Hitung total denda
            double totalDenda = 
                (input.getDendaBuahMentah() != null ? input.getDendaBuahMentah() : 0.0) +
                (input.getDendaBuahLewatMatang() != null ? input.getDendaBuahLewatMatang() : 0.0) +
                (input.getDendaTandanKosong() != null ? input.getDendaTandanKosong() : 0.0) +
                (input.getDendaGagangPanjang() != null ? input.getDendaGagangPanjang() : 0.0) +
                (input.getDendaBrondolan() != null ? input.getDendaBrondolan() : 0.0) +
                (input.getDendaBrondolanKotor() != null ? input.getDendaBrondolanKotor() : 0.0) +
                (input.getDendaBeratMinimal() != null ? input.getDendaBeratMinimal() : 0.0);
            
            input.setTotalDenda(totalDenda);
            
            // Hitung pembayaran bersih
            input.setPembayaranBersih(input.getTotalPembayaran() - totalDenda);
            
            return gradingResultRepository.save(input);
        } catch (Exception e) {
            throw new RuntimeException("Error dalam perhitungan: " + e.getMessage());
        }
    }

    public List<GradingResult> getAllGradingResults() {
        try {
            return gradingResultRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<GradingResult> getRecentGradings() {
        try {
            return gradingResultRepository.findTop10ByOrderByTanggalPengirimanDesc();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Double getTotalPembayaranHariIni() {
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();
            Double total = gradingResultRepository.sumTotalPembayaranBetweenDates(startOfDay, endOfDay);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double getTotalDendaHariIni() {
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();
            Double total = gradingResultRepository.sumTotalDendaBetweenDates(startOfDay, endOfDay);
            return total != null ? total : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Long getTotalTransaksiHariIni() {
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();
            Long total = gradingResultRepository.countByTanggalPengirimanBetween(startOfDay, endOfDay);
            return total != null ? total : 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    public Double getRataRataBeratHariIni() {
        try {
            LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();
            Double avg = gradingResultRepository.avgBeratTotalTBSBetweenDates(startOfDay, endOfDay);
            return avg != null ? avg : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
} 