package com.sparta.week6project.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class HomeWebController {
    @GetMapping("/home")
    public String getHomePage(Model model){
        System.out.println("Loading home page");
        return "home/home";
    }
}
