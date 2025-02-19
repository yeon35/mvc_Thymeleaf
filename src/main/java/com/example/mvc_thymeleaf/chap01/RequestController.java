package com.example.mvc_thymeleaf.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {

    @RequestMapping("/test")
    public String mainView(){
        return "index";
    }

    @RequestMapping("/test2")
    public String getMessage(Model model){
        model.addAttribute("testSTR", "test2 접속 구성 완료");
        return "testView";
    }




}
