package com.backend.product.domain;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductQualityDetailRepository extends MongoRepository<ProductQualityDetail, String> {
}
