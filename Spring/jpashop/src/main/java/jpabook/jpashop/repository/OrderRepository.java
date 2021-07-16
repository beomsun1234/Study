package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;


    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){

        return em.find(Order.class, id);
    }





}
