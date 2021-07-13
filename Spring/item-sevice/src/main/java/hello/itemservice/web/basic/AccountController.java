//package hello.itemservice.web.basic;
//
//import hello.itemservice.domain.User;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//@Controller
//@RequestMapping("account")
//public class AccountController {
//
//    @RequestMapping("/login")
//    public String handler(ModelMap model, HttpServletRequest request) {
//        Authentication auth = SecurityContextHolder.getContext()
//                .getAuthentication();
//        model.addAttribute("uri", request.getRequestURI());
//        model.addAttribute("user", auth.getName());
//        model.addAttribute("roles", auth.getAuthorities());
//        return "account/login";
//    }
//}
