package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughtStockEeception;
import jpabook.jpashop.repository.OrderRepository;
import org.hibernate.jdbc.Expectation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Fail.fail;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문(){
        //given
        Member member = createMember();

        Book book = createItem("시골 JAP",10000, 10);
        //when
        int ordercount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), ordercount);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 order");
        Assertions.assertEquals(1 , getOrder.getOrderItems().size() ,"주문한 상품 종류수가 정확해야한다");
        Assertions.assertEquals(10000* ordercount,getOrder.getTotalPrice(), "주문가격은 가격*수량이다");
        Assertions.assertEquals(8,book.getStockQuantity(), "주문 수량망큼  재고가 줄어야한다");
    }



    @Test
    public void 상품주문_재고수량초과() throws NotEnoughtStockEeception, Exception{
        //given
        Member member = createMember();
        Book book = createItem("jap",10000, 10);
        //when
        int ordercount = 11;
        //then
        Assertions.assertThrows(NotEnoughtStockEeception.class,()->{
            orderService.order(member.getId(),book.getId(),ordercount);
        });
    }

    @Test
    public void 주문취소(){
        //given
        Member member = createMember();
        Book book = createItem("시골 JPA", 10000,10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(),book.getId(),orderCount);
        //when
        orderService.cancleOrder(orderId);
        //then
        Order order = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.CANCEL,order.getStatus(), "주문취소상태는 cancel");
        Assertions.assertEquals(10,book.getStockQuantity(), "주문 취소만큼  재고가 증가해야야한다");
    }

    @Test
    public void 상품재고수량초과(){
        //given

        //when``

        //then
    }

    private Book createItem(String name , int price, int quantity) {
        Book book = new Book();
        book.setName("name");
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가", "111-111"));
        em.persist(member);
        return member;
    }

}