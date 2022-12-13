package com.tedu.straw.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable {

    Integer id;
    String username;
    String nickname;

    Integer questions;
    Integer collections;
}
