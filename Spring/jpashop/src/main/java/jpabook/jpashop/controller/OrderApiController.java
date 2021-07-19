package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class OrderApiController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

//    @GetMapping("/orders")
//    public String responseFindEntity(OrderSearch orderSearch){
//        List<Order> orders = orderService.searchOrder(orderSearch);
//        return orders.get(0).getMember().getName();
//        //dto로 처리해야함
//    }


}
