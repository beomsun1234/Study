package hello.Springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//slf4j

@Slf4j
@RestController //http메시지 바디에 넣어버림 REST API 만들때 핵심적인 컨트롤러
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTEst(){

        String name = "Spring";
        System.out.println("name = " + name);


        log.trace("trace log={}",name);
        log.debug("debug log={}", name);
        log.info("info log={}",name);
        log.warn("wan log={}", name);
        log.error("error log={}",name);

        return "ok";
    }
}
