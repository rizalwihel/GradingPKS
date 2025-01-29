package com.example.grading.GradingPKS.repository;

import com.example.grading.GradingPKS.model.GradingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GradingResultRepository extends JpaRepository<GradingResult, Long> {
    List<GradingResult> findTop10ByOrderByTanggalPengirimanDesc();
    
    @Query("SELECT SUM(g.totalPembayaran) FROM GradingResult g WHERE g.tanggalPengiriman BETWEEN ?1 AND ?2")
    Double sumTotalPembayaranBetweenDates(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT SUM(g.totalDenda) FROM GradingResult g WHERE g.tanggalPengiriman BETWEEN ?1 AND ?2")
    Double sumTotalDendaBetweenDates(LocalDateTime start, LocalDateTime end);
    
    Long countByTanggalPengirimanBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT AVG(g.beratTotalTBS) FROM GradingResult g WHERE g.tanggalPengiriman BETWEEN ?1 AND ?2")
    Double avgBeratTotalTBSBetweenDates(LocalDateTime start, LocalDateTime end);
} 