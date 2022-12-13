package com.tedu.birdnest.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tedu.birdnest.portal.mapper.ProductMapper;
import com.tedu.birdnest.portal.model.Product;
import com.tedu.birdnest.portal.service.IProductService;
import com.tedu.birdnest.portal.service.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Resource
    ProductMapper productMapper;

    @Override
    public PageInfo<Product> getProducts(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            throw new ServiceException("翻頁參數錯誤");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectList(new QueryWrapper<>());

        for (Product p:products) {
            p.setContext(null);
        }

        return new PageInfo<>(products);
    }
}
