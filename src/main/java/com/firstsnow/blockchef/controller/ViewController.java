package com.firstsnow.blockchef.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; // → templates/signup.html 렌더링
    }
}

