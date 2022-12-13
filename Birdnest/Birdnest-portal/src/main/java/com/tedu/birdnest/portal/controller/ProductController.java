package com.tedu.birdnest.portal.controller;


import com.github.pagehelper.PageInfo;
import com.tedu.birdnest.portal.model.Product;
import com.tedu.birdnest.portal.service.IProductService;
import com.tedu.birdnest.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/portal/product")
@Slf4j
public class ProductController {

    @Resource
    IProductService productService;

    @GetMapping("/products")
    public R<PageInfo<Product>> get(Integer pageNum) {
        log.debug("開始加載商品");
        if (pageNum == null) {
            pageNum = 1;
        }
        Integer pageSize = 8;

        PageInfo<Product> pageInfo = productService.getProducts(pageNum, pageSize);
        return R.ok(pageInfo);

    }

    @GetMapping("/{id}")
    public R<Product> detail(@PathVariable String id) {
        log.debug("get:{}", id);
        Product product = productService.getById(id);

        return R.ok(product);
    }

}
