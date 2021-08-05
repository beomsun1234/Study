package hello.hellospringsecurity.controller;


import hello.hellospringsecurity.oauth.SesstionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class homecontroller {
    private final HttpSession httpSession;
    @GetMapping("/")
    public String home(){
        log.info("start home");
        SesstionUser member = (SesstionUser)httpSession.getAttribute("member");
        if (member!=null){
            log.info("SessionMember={}",member.getName());
        }
        else{
            log.info("null");
        }
        return "index";
    }
}
