package com.tedu.birdnest.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/index.html")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @GetMapping("/login.html")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @GetMapping("/register.html")
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @GetMapping("/item-list.html")
    public ModelAndView item_list(){
        return new ModelAndView("item-list");
    }

    @GetMapping("/item.html")
    public ModelAndView item(){
        return new ModelAndView("item");
    }

    @GetMapping("/pay.html")
    public ModelAndView pay(){
        return new ModelAndView("pay");
    }

    @GetMapping("/pay-market.html")
    public ModelAndView pay_market(){
        return new ModelAndView("pay-market");
    }

}
