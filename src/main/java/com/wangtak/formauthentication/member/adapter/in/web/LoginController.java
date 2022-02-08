package com.wangtak.formauthentication.member.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Spring Security 에서 제공하는 Login 화면 대신
     * Custom 한 Login 화면을 보여주고 싶을 때 사용
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
