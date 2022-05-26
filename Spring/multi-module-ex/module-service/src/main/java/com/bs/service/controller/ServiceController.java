package com.bs.service.controller;

import com.bs.core.domain.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ServiceController {

    @GetMapping("")
    public Member getMember(){
        Member member = Member.builder().age(10).name("홍길동").build();
        return member;
    }
}
