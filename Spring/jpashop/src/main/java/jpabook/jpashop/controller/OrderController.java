package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.OrderForm;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItem();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }
    @PostMapping("/order")
    public String orderItem(OrderForm orderForm){
        log.info("orderForm={}",orderForm);
        orderService.order(orderForm.getMemberId(), orderForm.getItemId(), orderForm.getCount());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderItemList(OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrderAll(orderSearch);
        model.addAttribute("orders",orders);
        model.addAttribute("orderSerch", orderSearch);
        return "order/orderList";
    }
    @PostMapping("/orders/{orderId}/cancel")
    public String createCancel(@PathVariable Long orderId){
        orderService.cancleOrder(orderId);
        return "redirect:/orders";
    }
}
