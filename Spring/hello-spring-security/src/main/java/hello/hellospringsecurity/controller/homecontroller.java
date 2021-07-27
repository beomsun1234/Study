package hello.hellospringsecurity.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class homecontroller {

    @GetMapping("/")
    public String home(){
        log.info("start home");
        return "index";
    }
}
