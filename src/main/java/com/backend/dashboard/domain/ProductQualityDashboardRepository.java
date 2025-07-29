package com.backend.dashboard.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductQualityDashboardRepository extends MongoRepository<ProductQualityDashboard, String> {
    long countByLabel(String label);
    List<ProductQualityDashboard> findByLabel(String label);
    List<ProductQualityDashboard> findByUploadDateAfter(LocalDateTime date);
    List<ProductQualityDashboard> findAllByUploadDateBetween(LocalDateTime start, LocalDateTime end);

}