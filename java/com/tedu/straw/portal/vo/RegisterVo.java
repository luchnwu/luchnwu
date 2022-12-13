package com.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RegisterVo implements Serializable {

    @NotBlank(message = "邀請碼不能為空")
    private String inviteCode;
    @NotBlank(message = "電話號碼不能為空")
    @Pattern(regexp = "^09\\d{8}$", message = "必須是手機號碼")
    private String phone;
    @NotBlank(message = "暱稱不能為空")
    @Pattern(regexp = "^.{2,20}$", message = "暱稱是2-20個字符")
    private String nickname;
    @NotBlank(message = "密碼不能為空")
    @Pattern(regexp = "^\\w{4,20}$", message = "密碼是4-20個字母.數字._")
    private String password;
    @NotBlank(message = "確認密碼不能為空")
    private String confirm;
}
