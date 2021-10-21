package com.bs.helloredis.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    @Transient
    private String thumbnailImage;

    @Builder
    public Product(Long id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void updateProduct(String name, int price){
        this.name = name;
        this.price = price;
    }


}
