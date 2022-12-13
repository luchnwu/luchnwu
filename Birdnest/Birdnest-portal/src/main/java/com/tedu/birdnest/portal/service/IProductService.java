package com.tedu.birdnest.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.tedu.birdnest.portal.model.Product;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
public interface IProductService extends IService<Product> {

    PageInfo<Product> getProducts(Integer pageNum, Integer pageSize);

}
