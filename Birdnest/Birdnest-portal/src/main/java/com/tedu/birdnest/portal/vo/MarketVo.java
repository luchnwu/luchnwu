package com.tedu.birdnest.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Slf4j
public class MarketVo implements Serializable {

    private Integer productId;

    private Integer productCount;

    private String img;

    private String name;

    private String introduction;

    private Integer price;

    private Integer paymarketId;

}
