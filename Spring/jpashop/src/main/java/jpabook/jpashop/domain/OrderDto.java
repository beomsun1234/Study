package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
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

    @Data
    public static class Response{
        Long id;
        String returnMessage;

        public Response(Long id, String returnMessage) {
            this.id = id;
            this.returnMessage = returnMessage;
        }
    }

    @Data
    public static class OrderRequest{
        private Long memberId; //주문회원 id로 찾기
        private Long itemId; // itemId
        private int count;
        public OrderRequest(){
        }
    }


}
