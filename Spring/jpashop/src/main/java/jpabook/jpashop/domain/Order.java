package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "oders")
@Getter @Setter
public class Order{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY) //lazy = 지연로딩 // ManyTo x -> 다 lazy로 바꾸기 기본타입(eager)
    @JoinColumn(name = "member_id") //fk키
    private Member member;
    //jpql select o from orders o -> sql select * from orders  처음오더나오는 쿼리  n(맴버를 가져오기위함)+1(order) 문제 //

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL) //기본 One to x/fetch전략은 lazy
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") //오더에 fk를 둔다 이유는 주로 엑세스를 많이 하는 곳에 둔다 , 오더를 보면서 딜리버리를 본다
    private Delivery delivery;


    private LocalDateTime orderDate;  //주문시간

    @Enumerated(EnumType.STRING)//타입은 항상 스트링으로 중간에 값이 추가되도 괜찮음
    private OrderStatus status;  // 주문상태 ORDER, CANCEL


     //--연관관계 메서드-------------------------//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    //---------생성 메서드-------/
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    //----비지니스로직--------//
    /**
     * 주문취소
     */
    public void cancel(){
        if (delivery.getStatus()==DeliveryStatus.COMPLETE){
            throw  new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems){
            orderItem.cancel();
        }
    }


    //----조회로직---------//

    /**
     * 전체주문 가격조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems){
            totalPrice+=orderItem.getTotalPrice();
        }
        return totalPrice;
    }

//    public int getTotalPrice(){
//        int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
//        return totalPrice;
//    }


}
