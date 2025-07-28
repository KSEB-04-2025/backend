package com.backend.product.application;

import com.backend.product.domain.ProductQualityDetail;
import com.backend.product.domain.ProductQualityDetailRepository;
import com.backend.product.exception.ProductListException;
import com.backend.product.presentation.dto.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductListService {

    private final ProductQualityDetailRepository repository;

    public List<ProductListResponse> getAllProducts() {
        List<ProductQualityDetail> all = repository.findAll();
        if (all.isEmpty()) {
            throw new ProductListException("PL001", "제품 목록이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
        return all.stream()
                .map(pq -> ProductListResponse.builder()
                        .productId(pq.getId())
                        .label(pq.getLabel())
                        .uploadDate(pq.getUploadDate())
                        .build())
                .collect(Collectors.toList());
    }
}
