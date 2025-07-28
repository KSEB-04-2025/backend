package com.backend.product.presentation;

import com.backend.product.presentation.swagger.ProductListSwagger;
import com.backend.product.application.ProductListService;
import com.backend.product.presentation.dto.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductListController implements ProductListSwagger {

    private final ProductListService productListService;

    @Override
    public List<ProductListResponse> getAllProducts() {
        return productListService.getAllProducts();
    }
}
