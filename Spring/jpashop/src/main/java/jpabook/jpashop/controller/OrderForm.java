package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {
    Long memberId;
    Long itemId;
    int count;
}
