package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class MemberApiController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원 단일 조회
    @GetMapping("/members/{memberId}")
    public ResponseEntity<Member> findBtyId(@PathVariable Long memberId){
        Member member = memberService.findOne(memberId);
        return new ResponseEntity<>(member,HttpStatus.OK);
    }
    //회원 전체조회
    @GetMapping("/members")
    public ResponseEntity<List<Member>> findAll(){
        List<Member> members = memberService.findMembers();
        return new ResponseEntity<>(members ,HttpStatus.OK);
    }
    //회원등록
    @PostMapping("/members")
    public ResponseEntity<MemberForm> joinMember(@RequestBody MemberForm memberForm){
        Member member = new Member();
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getHomecode());
        member.setName(memberForm.getName());
        member.setAddress(address);
        memberService.join(member);
        // 전부 dto로 바꾸기
        return new ResponseEntity<>(memberForm, HttpStatus.OK);
    }
    //회원수정
    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberForm> updateMember(@PathVariable Long memberId, @RequestBody MemberForm memberForm){
        memberService.update(memberId,memberForm);
        return new ResponseEntity<>(memberForm,HttpStatus.OK);
    }
    //회원삭제
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long memberId){
        Long deleteMemberId = memberService.delete(memberId);
        return new ResponseEntity<>(deleteMemberId,HttpStatus.OK);
    }
}
