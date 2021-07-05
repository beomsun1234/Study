package hello.Springmvc.basic.requestmapping;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping(value = {"/hello-basic","/hello-go"},method = RequestMethod.GET) //or조건
    public String requestMapping(){
        log.info("hellobasic");
        return "ok";
    }


}
