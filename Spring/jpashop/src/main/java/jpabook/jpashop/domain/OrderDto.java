package jpabook.jpashop.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order){
        orderId = order.getId();
        name = order.getMember().getName(); // lazy
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
        address = order.getDelivery().getAddress(); // lazy
    }
    @Data
    @NoArgsConstructor
    public static class OrderItemDto {
        private String itemName;
        private int orderPrice;
        private int count;

        @Builder
        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
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
    }


}
