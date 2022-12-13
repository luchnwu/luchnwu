package com.tedu.birdnest.portal.service;

import com.tedu.birdnest.portal.model.Paymarket;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tedu.birdnest.portal.vo.MarketVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-21
 */
public interface IPaymarketService extends IService<Paymarket> {

    void saveMarketVo(MarketVo marketVo);

    List<MarketVo> getMarkets();

}
