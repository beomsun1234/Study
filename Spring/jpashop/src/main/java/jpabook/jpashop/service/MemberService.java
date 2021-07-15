package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor//파이널만 설정
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;


    /**
     * 회원가입
     *
     * 회원 전체 조회
     */

    //회원가입
    public Long join(Member member){
        validateDuplicateMember(member); //중복검증
        memberRepository.save(member);
        return member.getId(); //아이디를 리턴해야 머가 저장되었는지 알수있음
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.finByName(member.getName()); //맴버수를 센 후 0크면 문제있음 으로 하는게 더 효율적
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //회원 한명조회
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}
