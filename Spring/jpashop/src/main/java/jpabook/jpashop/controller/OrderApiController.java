package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderDto;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class OrderApiController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> orderFindById(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.findOrderById(orderId),HttpStatus.OK);
    }


    //주문 조회 전체
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> responseFindEntity(){
        List<Order> orders = orderService.findOrderAll(new OrderSearch());
        return new ResponseEntity<>(orders.stream().map(OrderDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    //주문하기
    @PostMapping("/orders")
    public ResponseEntity<OrderDto.Response> requestOrder(@RequestBody OrderDto.OrderRequest orderRequest){
        Long memberId = memberService.findOne(orderRequest.getMemberId()).getId(); // findByName만들기
        Long itemId = itemService.findOne(orderRequest.getMemberId()).getId();     //
        orderService.order(memberId,itemId,orderRequest.getCount());
        return new ResponseEntity<>(new OrderDto.Response(memberId,memberId+"님 주문완료"),HttpStatus.OK);
    }
    //주문 취소 할경우 주문 상태를 바꾸기에 put
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto.Response> cancelOrder(@PathVariable Long orderId){
        orderService.cancleOrder(orderId);
        return new ResponseEntity<>(new OrderDto.Response(orderId,orderId+"주문취소완료"),HttpStatus.OK);
    }


}
