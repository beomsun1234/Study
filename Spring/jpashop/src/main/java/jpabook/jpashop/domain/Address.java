package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;



@Embeddable
@Getter
public class Address {

    private String city;


    private String street;
    private String homecode;
    protected Address(){

    }
    public Address(String city, String street, String homecode) {
        this.city = city;
        this.street = street;
        this.homecode = homecode;
    }


}
