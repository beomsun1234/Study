package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



@RequestMapping("/springmvc/v3/members")
@Controller
public class SpringMvcControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    //get-> 조회에 많이 사용용됨
    //@RequestMapping(value = "/new-form",method = RequestMethod.GET)
    @GetMapping("/new-form")
    public String processNewForm(){
        return "new-form"; //그냥 뷰이름으로 알고 반환
    }

    //@RequestMapping(value = "/save",method = RequestMethod.POST)
    @PostMapping("/save")
    public String processSave(@RequestParam("username") String username, //HttpServletRequest request, HttpServletResponse response
                              @RequestParam("age") int age,
                              Model model) {  //모델에 데이터를 담아야하니까 모델이 필요

        Member member = new Member(username,age);
        memberRepository.save(member);
        ModelAndView mv  = new ModelAndView("save-result");
        model.addAttribute("member",member);
        return "save-result";
    }

    // /springmvc/v3/members
    //@RequestMapping(method = RequestMethod.GET) ->request.getparam과 같은 느낌, GET쿼리파라미터, POST form 방식을 모두 지원
    @GetMapping
    public String processMembers(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);
        return "member";
    }
}
