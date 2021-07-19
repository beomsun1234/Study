package jpabook.jpashop.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderDto(Order order){
        orderId = order.getId();
        name = order.getMember().getName(); // lazy
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress(); // lazy
    }
}
