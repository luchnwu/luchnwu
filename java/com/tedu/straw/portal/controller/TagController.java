package com.tedu.straw.portal.controller;


import com.tedu.straw.portal.model.Tag;
import com.tedu.straw.portal.service.ITagService;
import com.tedu.straw.portal.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.com
 * @since 2022-10-04
 */
@RestController
@RequestMapping("/v1/tags")
public class TagController {

    @Autowired
    ITagService tagService;

    @GetMapping("")
    public R<List<Tag>> tags() {
        List<Tag> list = tagService.getTags();
        return R.ok(list);
    }

}
