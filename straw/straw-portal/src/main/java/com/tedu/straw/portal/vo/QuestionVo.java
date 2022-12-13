package com.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class QuestionVo implements Serializable {

    @NotBlank(message = "標題不能為空")
    @Pattern(regexp = "^.{3,50}$", message = "標題需要3-50個字符")
    private String title;

    private String[] tagNames = {};

    private String[] teacherNicknames = {};
    @NotBlank(message = "問題內容不能為空")
    private String content;
}
