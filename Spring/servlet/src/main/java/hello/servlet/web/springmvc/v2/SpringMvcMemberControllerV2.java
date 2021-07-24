package hello.servlet.web.springmvc.v2;


import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RequestMapping("/springmvc/v2/members")
@Controller
public class SpringMvcMemberControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")
    public ModelAndView processNewForm(){
        return new ModelAndView("new-form");
    }

    @RequestMapping("/save")
    public ModelAndView processSave(HttpServletRequest request, HttpServletResponse
            response) {
        String username =request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        Member member = new Member(username,age);
        memberRepository.save(member);
        ModelAndView mv  = new ModelAndView("save-result");
        mv.getModel().put("member",member);
        return mv;
    }

    // /springmvc/v2/members
    @RequestMapping
    public ModelAndView processMembers(){
        List<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("member");
        mv.getModel().put("members", members);

        return mv;
    }


}
