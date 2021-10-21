package com.bs.helloredis.service;

import com.bs.helloredis.domain.Product;
import com.bs.helloredis.domain.dto.ProductInfo;
import com.bs.helloredis.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 상품 저장, 상품 저장시 캐시 삭제
     * @return
     */
    @CacheEvict(cacheNames = "products", allEntries = true)
    @Transactional
    public String createProduct(){
        List<Product> products = new ArrayList<>();
        for(int i =1; i<=20; i++){
            products.add(Product.builder().price(10000 + i).name("테스트" + i).build());
        }
        productRepository.saveAll(products);
        return "성공";
    }
    /**
     * 전체 조회
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "1", unless = "#result.size()< 1")
    public List<ProductInfo> findAll(){
        return productRepository.findAll().stream().map(product -> ProductInfo.builder().product(product).build()).collect(Collectors.toList());
    }

    /**
     * 단건 조회
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "product", key = "#id", unless = "#result == 0 ")
    public ProductInfo findOneById(Long id){
        return ProductInfo.builder().product(productRepository.findById(id).orElseThrow()).build();
    }

    /**
     * 상품 수정시 전체 조회 캐시 삭제
     * 단건 조회시 저장된 캐시정보 수정
     * @return
     */
    @Caching(
            evict = @CacheEvict(cacheNames = "products", allEntries = true)
    )
    @CachePut(value = "product", key = "#id", unless = "#result == 0 ")
    public ProductInfo updateProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow();
        product.updateProduct("수정테스트",15000);
        return ProductInfo.builder().product(productRepository.save(product)).build();
    }
}
