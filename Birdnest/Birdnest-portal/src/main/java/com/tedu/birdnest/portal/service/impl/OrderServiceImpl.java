package com.tedu.birdnest.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.birdnest.portal.mapper.OrderMapper;
import com.tedu.birdnest.portal.model.Order;
import com.tedu.birdnest.portal.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
