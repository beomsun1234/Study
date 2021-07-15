package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughtStockEeception;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //하나의 테이블에 전부 집어넣음
@DiscriminatorColumn(name = "dtype") //만약 book이면 어떻게 할꺼냐
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;


    private String name;
    private int price;
    private int stockQuantity;


    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스로직==// 재고가 늘고 줄고 엔티티자체에서 해결할수있을경우 엔티티안에 넣는다 ps..재고수량을 가지고 있는 곳에서 로직작성하면 응집도 높아짐

    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity+=quantity;

    }
    /**
     *  stock감소
     */
    public void removeStock(int quantity){
        int restStockl = this.stockQuantity - quantity;
        if (restStockl< 0){
            throw new NotEnoughtStockEeception("need more stock");
        }
        this.stockQuantity = restStockl;
    }



}
