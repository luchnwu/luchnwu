package com.tedu.birdnest.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {

    @NotBlank(message = "帳號不能為空")
    @Pattern(regexp = "^([A-Za-z0-9_\\-.])+@([A-Za-z0-9_\\-.])+\\.([A-Za-z]{2,4})$", message = "必須符合信箱規範,包含*@*mail.com")
    private String username;
    @NotBlank(message = "密碼不能為空")
    @Pattern(regexp = "^\\w{4,20}$", message = "密碼是4-20個字母.數字._")
    private String password;
    @NotBlank(message = "確認密碼不能為空")
    @Pattern(regexp = "^\\w{4,20}$", message = "密碼是4-20個字母.數字._")
    private String confirm;
    @NotBlank(message = "姓名不能為空")
    @Pattern(regexp = "^.{2,5}$", message = "名字為2-5字符")
    private String name;
    @NotBlank(message = "電話不能為空")
    @Pattern(regexp = "^09\\d{8}$", message = "必須是手機號碼")
    private String phone;

}
