package com.tedu.straw.portal.controller;

import com.tedu.straw.portal.service.ServiceException;
import com.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public R handleServiceException(ServiceException e) {
        log.debug("處理業務異常");
        log.error("業務異常", e);
        return R.failed(e);
    }

    @ExceptionHandler
    public R handleException(Exception e) {
        log.error("其他異常", e);
        return R.failed(e);
    }
}
