package com.bs.hellokubernetes;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "hello-kubernetes";
    }
    @GetMapping("/version")
    public String updateTest(){
        return "version";
    }
}
