package com.sparta.week6project.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class LoginController {
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/home")
    public String postLoginSuccess(){
        return "home/home";
    }
}
