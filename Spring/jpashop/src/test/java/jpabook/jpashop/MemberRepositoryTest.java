package jpabook.jpashop;

import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void save(){
        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//        //when
//        Long saveId = memberRepository.save(member);
//        //then
//        Member findmember =  memberRepository.findById(saveId);
//
//        Assertions.assertThat(findmember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findmember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findmember).isEqualTo(member);
    }

}