package jpabook.jpashop.service;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //주문 테이터번경

    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티조히
        Member member= memberRepository.findById(memberId);
        Item item = itemRepository.findOne(itemId);
        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장장
       orderRepository.save(order);
        return order.getId();
    }

    public List<OrderDto> findAll(){
        List<Order> orderList = orderRepository.findAllFetchJoin();
        return orderList.stream().map(o->new OrderDto(o)).collect(Collectors.toList());
    }
    //취소
    @Transactional
    public void cancleOrder(Long orderId) throws IllegalStateException {
        //엔티티조회
        Order order = orderRepository.findOne(orderId);
        //취소
        order.cancel();
    }
    //모두 검색
    public List<Order> findOrderAll(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
    //한명검색
    public OrderDto findOrderById(Long orderId){
        return new OrderDto(orderRepository.findOne(orderId));
    }


}
