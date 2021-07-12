//package hello.itemservice.web.basic;
//
//import hello.itemservice.domain.User;
//import hello.itemservice.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@Controller
//@RequestMapping("account")
//public class AccountController {
//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/login")
//    public String login(){
//        log.info("login페이지");
//        return "account/login";
//    }
//
//    @GetMapping("/register")
//    public String getregister(User user){
//        log.info("register페이지");
//        log.info("user={}",user.getPassword());
//        return "account/register";
//    }
//    @PostMapping("/register")
//    public String register(User user){
//        log.info("name={}",user);
//        userService.save(user);
//        return "redirect:/basic/items";
//    }
//}
