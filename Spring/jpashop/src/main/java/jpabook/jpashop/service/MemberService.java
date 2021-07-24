package jpabook.jpashop.service;

import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.controller.form.SignUpForm;
import jpabook.jpashop.controller.form.UserInfo;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@org.springframework.transaction.annotation.Transactional(readOnly = true)
@RequiredArgsConstructor//파이널만 설정
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String regex = "^(.+)@(.+)$";

    /**
     * 회원가입
     *
     *
     */
    @Transactional
    public Long joinV2(SignUpForm signUpForm){
        Member member = new Member();
        
        member.setName(signUpForm.getName());
        member.setEmail(signUpForm.getEmail());
        member.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        member.setRole("USER");
        member.setAddress(new Address(signUpForm.getCity(),signUpForm.getStreet(),signUpForm.getHomecode()));
        validateDuplicateMember(member); //중복검증
        memberRepository.save(member);
        return member.getId(); //아이디를 리턴해야 머가 저장되었는지 알수있음
    }

    /**
     * 회원가입
     *
     *  엔티티 그대로 받는 버전
     */
    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복검증
        memberRepository.save(member);
        return member.getId(); //아이디를 리턴해야 머가 저장되었는지 알수있음
    }

    /**
     * 회원 수정
     * @param memberId
     * @param memberForm
     * @return
     */
    @Transactional
    public Long update(Long memberId, MemberForm memberForm){
        Member member = memberRepository.findById(memberId);
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getHomecode());
        member.setName(memberForm.getName());
        member.setAddress(address);
        validateDuplicateMember(member);
        return member.getId();
    }

    /**
     * 유효성 체크 이름 같은지
     * @param member
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.finByName(member.getName()); //맴버수를 센 후 0크면 문제있음 으로 하는게 더 효율적
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    private void EmailValidation(String email){
        EmailValidator validator = new EmailValidator();
        validator.isValid(email,null);
    }
    /**
     * 회원삭제
     * @param memberId
    **/
    @Transactional
    public Long delete(Long memberId){
        memberRepository.delete(memberId);
        return memberId;
    }

    /**
     * 회원 전체조회
     * @return
     */
    //회원전체 조회
    public List<UserInfo> findMembers(){
        List<Member> members = memberRepository.findAll();
        return members.stream().map(UserInfo::new).collect(Collectors.toList());
    }

    /**
     * 회원 한명조회
     * @param memberId
     * @return
     */
    public UserInfo findOne(Long memberId){
        Member member = memberRepository.findById(memberId);
        return UserInfo.builder().member(member).build();
    }

    /**
     * spirng securetiy
     * 입력한 email를 이용해 회원을 조회한 후  회원 정보와 권한 정보가 담긴 Member 클래스를 반환합니다
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email); // 수정완료

        if (member == null){
            throw new UsernameNotFoundException(email+" is not found");
        }
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return authorities;
            }

            @Override
            public String getPassword() {
                return member.getPassword();
            }

            @Override
            public String getUsername() {
                return member.getName();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
        return userDetails;
    }
}
