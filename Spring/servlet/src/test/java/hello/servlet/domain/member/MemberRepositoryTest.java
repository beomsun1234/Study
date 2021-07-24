package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();// 생성자 인스턴스로


    @AfterEach
    void afterEach(){
        //테스트가 끝나면 초기화
        memberRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Member member = new Member("pakr",20);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member finMember = memberRepository.findById(savedMember.getId());
        assertThat(finMember).isEqualTo(savedMember); //assertj 사용 찾은맴버랑 저장된 맴버가 같아얗나다
    }
    @Test
    void findAll(){
        //given
        Member member1= new Member("pakr",20);
        Member member2 = new Member("pakr2",30);
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1,member2); //결과에 맴버1 , 2 있는지 검사



    }
}
