package com.tedu.birdnest.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tedu.birdnest.portal.mapper.AccountMapper;
import com.tedu.birdnest.portal.mapper.ProductMapper;
import com.tedu.birdnest.portal.model.Account;
import com.tedu.birdnest.portal.model.Paymarket;
import com.tedu.birdnest.portal.mapper.PaymarketMapper;
import com.tedu.birdnest.portal.model.Product;
import com.tedu.birdnest.portal.service.IAccountService;
import com.tedu.birdnest.portal.service.IPaymarketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.birdnest.portal.service.ServiceException;
import com.tedu.birdnest.portal.vo.MarketVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-21
 */
@Service
@Slf4j
public class PaymarketServiceImpl extends ServiceImpl<PaymarketMapper, Paymarket> implements IPaymarketService {

    @Resource
    PaymarketMapper paymarketMapper;
    @Resource
    ProductMapper productMapper;
    @Autowired
    IAccountService accountService;
    @Resource
    AccountMapper accountMapper;

    @Override
    public void saveMarketVo(MarketVo marketVo) {
        //將使用者選購商品及數量及使用者資訊保存
        log.debug("marketVo:{}", marketVo);
        String username = accountService.currentUsername();
//        if (username == null) {
//            return "/login";
//        }
        Account account = accountMapper.findAccountByUsername(username);

        //存入資料庫
        Paymarket paymarket = new Paymarket()
                .setAccountId(account.getId())
                .setProductId(marketVo.getProductId())
                .setProductCount(marketVo.getProductCount());
//        log.debug("marketVo:{}", paymarket);

        //確認購物車資料庫沒有品項 有則更新 沒有才新增
        QueryWrapper<Paymarket> query = new QueryWrapper<Paymarket>()
                .eq("account_id", paymarket.getAccountId())
                .eq("product_id", paymarket.getProductId());

        if (paymarketMapper.selectOne(query) == null) {
            paymarketMapper.insert(paymarket);
        } else {
            paymarketMapper.update(paymarket, query);
        }
//        return null;
    }

    @Override
    public List<MarketVo> getMarkets() {
        //找出使用者
        String username = accountService.currentUsername();
        Account account = accountMapper.findAccountByUsername(username);

        //找出使用者在資料庫中的購物車
        List<Paymarket> paymarkets = paymarketMapper.selectList(new QueryWrapper<Paymarket>()
                .eq("account_id", account.getId()));

        //將購物車內容放入頁面資訊
        List<MarketVo> markets = new ArrayList<>();

        //將購物車商品內容找出來
        for (Paymarket p : paymarkets) {
            Product product = productMapper.selectById(p.getProductId());
            MarketVo market = new MarketVo()
                    .setProductId(product.getId())
                    .setImg(product.getImg())
                    .setName(product.getName())
                    .setIntroduction(product.getIntroduction())
                    .setPrice(product.getPrice())
                    .setProductCount(p.getProductCount())
                    .setPaymarketId(p.getId());
            markets.add(market);
        }

        return markets;
    }
}
