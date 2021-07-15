package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("park");
        //when
        Long id = memberService.join(member);
        //then
        em.flush();
        assertEquals(member,memberService.findOne(id));
    }

    @Test
    public void 중복회원체크() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        }catch (IllegalStateException e) {
            return;
        }
        //then
        fail("예외가 발생해야한다.");

    }
}