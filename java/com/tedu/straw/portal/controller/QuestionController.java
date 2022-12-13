package com.tedu.straw.portal.controller;


import com.github.pagehelper.PageInfo;
import com.tedu.straw.portal.model.Question;
import com.tedu.straw.portal.service.IQuestionService;
import com.tedu.straw.portal.service.ServiceException;
import com.tedu.straw.portal.vo.QuestionVo;
import com.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/v1/questions")
@Slf4j
public class    QuestionController {

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/my")
    public R<PageInfo<Question>> my(Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = 8;

        try {
            log.debug("準備查詢當前用戶的問題");
            PageInfo<Question> pageInfo = questionService.getMyQuestions(pageNum, pageSize);
            return R.ok(pageInfo);
        } catch (ServiceException e) {
            log.error("查詢當前用戶的問題失敗", e);
            return R.failed(e);
        }
    }

    @PostMapping("")
    public R<String> createQuestion(@Validated QuestionVo questionVo, BindingResult validaResult) {
        if (validaResult.hasErrors()) {
            String message = validaResult.getFieldError().getDefaultMessage();
            return R.unprocessableEntity(message);
        }
        if (questionVo.getTagNames().length == 0) {
            return R.unprocessableEntity("沒有選擇標籤");
        }
        if (questionVo.getTeacherNicknames().length == 0) {
            return R.unprocessableEntity("沒有選擇老師");
        }
        log.debug("收到表單信息{}", questionVo);
        questionService.saveQuestion(questionVo);
        return R.ok("添加問題成功");
    }
}
