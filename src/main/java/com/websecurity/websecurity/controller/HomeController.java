package com.websecurity.websecurity.controller;

import com.websecurity.websecurity.auth.MyUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if(authentication != null) {
            //로그인 성공시
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            log.info(authorities.toString());
            model.addAttribute("info", authorities.toString());
        }
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/my")
    public String my() {
        return "my";
    }
}
