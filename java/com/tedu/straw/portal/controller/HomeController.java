package com.tedu.straw.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/question/create.html")
    public ModelAndView create(){
        return new ModelAndView("question/create");
    }
}
