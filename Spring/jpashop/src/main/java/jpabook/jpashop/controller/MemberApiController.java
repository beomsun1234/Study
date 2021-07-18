package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class MemberApiController {
    private final MemberService memberService;
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findAll(){
        List<Member> members = memberService.findMembers();
        return new ResponseEntity<>(members ,HttpStatus.OK);
    }

}
