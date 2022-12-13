package com.tedu.birdnest.portal.controller;


import com.github.pagehelper.PageInfo;
import com.tedu.birdnest.portal.model.Paymarket;
import com.tedu.birdnest.portal.model.Product;
import com.tedu.birdnest.portal.service.IAccountService;
import com.tedu.birdnest.portal.service.IPaymarketService;
import com.tedu.birdnest.portal.service.IProductService;
import com.tedu.birdnest.portal.vo.MarketVo;
import com.tedu.birdnest.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @since 2022-11-21
 */
@RestController
@RequestMapping("/portal/paymarket")
@Slf4j
public class PaymarketController {

    @Resource
    IPaymarketService paymarketService;

    @GetMapping("/market")
    public void addMarket(MarketVo marketVo) {

        paymarketService.saveMarketVo(marketVo);

    }

    @GetMapping("/markets")
    public R<List<MarketVo>> getMarkets() {
        List<MarketVo> markets = paymarketService.getMarkets();

        return R.ok(markets);
    }

    @GetMapping("/increase/{id}")
    public Integer increase(@PathVariable String id) {
        log.debug("increase:{}", id);
        Paymarket paymarket = paymarketService.getById(id);
        Integer count = paymarket.getProductCount();
        count++;

        paymarket.setProductCount(count);
        paymarketService.updateById(paymarket);

        return count;
    }

    @GetMapping("/decrease/{id}")
    public Integer decrease(@PathVariable String id) {
        log.debug("increase:{}", id);
        Paymarket paymarket = paymarketService.getById(id);
        Integer count = paymarket.getProductCount();
        count--;

        paymarket.setProductCount(count);
        paymarketService.updateById(paymarket);

        return count;
    }

    @GetMapping("/del/{id}")
    public void del(@PathVariable String id) {
        log.debug("increase:{}", id);

        paymarketService.removeById(id);
    }

}
