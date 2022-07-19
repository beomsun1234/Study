package com.bs.helloaop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api")
@RestController
public class HelloAopController {


    @LogExecutionTime
    @GetMapping("v1")
    public String helle(){
        return "hello";
    }

    @GetMapping("v2")
    public String helle2(){
        return "hello2";
    }





}
