package hello.hellospringsecurity.controller;


import hello.hellospringsecurity.domain.Member;
import hello.hellospringsecurity.oauth.SesstionUser;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final HttpSession httpSession;

    @GetMapping("/loginForm")
    public String openLoginForm(){
        return "loginForm";
    }

    @GetMapping("/member")
    public String test(Model model){

        SesstionUser user = (SesstionUser) httpSession.getAttribute("member");
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "member/v1";
    }

}
