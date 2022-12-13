package com.tedu.birdnest.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderVo implements Serializable {

    private List<MarketVo> markets;
    @NotBlank(message = "不能為空")
    private String pay_method;
    @NotBlank(message = "不能為空")
    private String order_username;
    @NotBlank(message = "不能為空")
    private String order_email;
    @NotBlank(message = "不能為空")
    private String order_address;
    @NotBlank(message = "不能為空")
    private String order_phone;
    @NotBlank(message = "不能為空")
    private String recipient_username;
    @NotBlank(message = "不能為空")
    private String recipient_email;
    @NotBlank(message = "不能為空")
    private String recipient_address;
    @NotBlank(message = "不能為空")
    private String recipient_phone;
}
