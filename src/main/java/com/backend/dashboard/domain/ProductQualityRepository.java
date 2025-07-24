package com.backend.dashboard.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductQualityRepository extends MongoRepository<ProductQuality, String> {
    long countByLabel(String label);
    List<ProductQuality> findByLabel(String label);
    List<ProductQuality> findByUploadDateAfter(LocalDateTime date);

}