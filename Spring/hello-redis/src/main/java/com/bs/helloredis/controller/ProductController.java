package com.bs.helloredis.controller;

import com.bs.helloredis.domain.dto.ProductInfo;
import com.bs.helloredis.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * 20개 상품 생성
     * @return
     */
    @PostMapping("product")
    public String createProduct(){
        return productService.createProduct();
    }

    /**
     * 전체 조회
     * @return
     */
    @GetMapping("product")
    public List<ProductInfo> findAll(){
        return productService.findAll();
    }

    /**
     * 단건 조회
     * @return
     */
    @GetMapping("product/{id}")
    public ProductInfo findOneById(@PathVariable Long id){
        return productService.findOneById(id);
    }

    @PutMapping("product/{id}")
    public ProductInfo updateProduct(@PathVariable Long id){
        return productService.updateProduct(id);
    }

}
