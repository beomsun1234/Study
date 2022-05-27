package com.bs.core.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    protected Member(){
    }

    @Builder
    public Member(Long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
