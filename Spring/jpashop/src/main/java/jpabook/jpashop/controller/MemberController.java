package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result){
        // member대신 폼을 넣는이유는   //  화면에 맞는 데이터만 폼으로 만들어서 데이터르 받는게 낫다 //->딱 필요한 데이터만을 엔티티로 넘긴다
        if (result.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(),form.getStreet(),form.getHomecode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/"; //첫번째 페이지로
    }
    @GetMapping("/members")
    public String getMembers(Model model){
        //dto로 변환후 반환하자!! , API를 만들때 절때 엔티티 외부 반환 XXXX
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
