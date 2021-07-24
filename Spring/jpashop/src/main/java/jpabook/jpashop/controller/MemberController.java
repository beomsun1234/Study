package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.MemberForm;
import jpabook.jpashop.controller.form.SignUpForm;
import jpabook.jpashop.controller.form.UserInfo;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

//   @GetMapping("/sign")
//   public String createSignUpForm(){
//       return "createSigUpForm";
//   }
//    @PostMapping("/sign")
//    public String addMember(@ModelAttribute SignUpForm signUpForm){
//       log.info("완료","완성됨");
//        memberService.joinV2(signUpForm);
//        return "redirect:/";
//    }

    /**
     * @param model -> signUpMemberForm
     * 회원가입
     * @return
     */
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("signUpMemberForm", new SignUpForm());
        return "members/createMemberForm";
    }

    /**
     * @param form -> signUpMemberForm
     * 회원가입
     * @return
     */
    @PostMapping("/members/new")
    public String createMember(@Valid SignUpForm form, BindingResult result){
        // member대신 폼을 넣는이유는   //  화면에 맞는 데이터만 폼으로 만들어서 데이터르 받는게 낫다 //->딱 필요한 데이터만을 엔티티로 넘긴다
        if (result.hasErrors()){
            return "members/createMemberForm";
        }
        memberService.joinV2(form);
        return "redirect:/"; //첫번째 페이지로
    }

    /**
     * 회원조회
     * @param model
     * @return
     */
    @GetMapping("/members")
    public String getMembers(Model model){
        //dto로 변환후 반환하자!! , API를 만들때 절때 엔티티 외부 반환 XXXX
        List<UserInfo> userInfo = memberService.findMembers();
        model.addAttribute("members", userInfo);
        return "members/memberList";
    }
}
