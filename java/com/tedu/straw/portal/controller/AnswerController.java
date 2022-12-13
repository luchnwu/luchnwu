package com.tedu.straw.portal.controller;


import com.tedu.straw.portal.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
@RestController
@RequestMapping("/portal/answer")
public class AnswerController {

    @Autowired
    IAnswerService answerService;

}
