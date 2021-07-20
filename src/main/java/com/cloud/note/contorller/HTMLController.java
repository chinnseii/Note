package com.cloud.note.contorller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTMLController {
    @RequestMapping("login")
    public String login() {
        return "login";   //注意：通过thymeleaf访问的界面不应添加后缀
    }
    @RequestMapping("register")
    public String register() {
        return "register";   //注意：通过thymeleaf访问的界面不应添加后缀
    }
    @RequestMapping("index")
    public String index() {
        return "index";   //注意：通过thymeleaf访问的界面不应添加后缀
    }
}
