package com.tedu.birdnest.portal.controller;


import com.tedu.birdnest.portal.service.IPaymarketService;
import com.tedu.birdnest.portal.vo.MarketVo;
import com.tedu.birdnest.portal.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@RestController
@RequestMapping("/portal/order")
@Slf4j
public class OrderController {

    @Resource
    IPaymarketService paymarketService;

    @PostMapping("")
    public void getOrder(OrderVo orderVo) {
        List<MarketVo> markets = paymarketService.getMarkets();
        orderVo.setMarkets(markets);

        log.debug("order:{}", orderVo);
    }

}
