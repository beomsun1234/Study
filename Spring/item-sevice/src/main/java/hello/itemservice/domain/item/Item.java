package hello.itemservice.domain.item;


import hello.itemservice.domain.User;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//위험함,getter setter 사용  dto일경우 data써도 괜찮음 하지만 확인은해야함

@Data
@Getter @Setter
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //id
    private String itemName; //아이템이름
    private Integer price; // 가격이 0이면 애매하니 인티저로
    private Integer quantity; //수량

    public Item(){
    }

    public Item(String itemName, Integer price, Integer quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }



}
