package com.bs.helloredis.domain.dto;

import com.bs.helloredis.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ProductInfo implements Serializable {
    private Long productId;
    private String name;
    private int price;

    @Builder
    public ProductInfo(Product product){
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
