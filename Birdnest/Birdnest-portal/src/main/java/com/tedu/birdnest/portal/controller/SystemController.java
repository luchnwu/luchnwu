package com.tedu.birdnest.portal.controller;


import com.tedu.birdnest.portal.service.IUserService;
import com.tedu.birdnest.portal.vo.R;
import com.tedu.birdnest.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class SystemController {

    @Resource
    IUserService userService;

    @PostMapping("/register")
    public R register(@Validated RegisterVo registerVo, BindingResult validaResult) {
        log.debug("註冊表單:{}", registerVo);
        //使用驗證框架
        if (validaResult.hasErrors()) {
            String error = validaResult.getFieldError().getDefaultMessage();
            return R.unprocessableEntity(error);
        }

        return userService.register(registerVo);
    }

}
