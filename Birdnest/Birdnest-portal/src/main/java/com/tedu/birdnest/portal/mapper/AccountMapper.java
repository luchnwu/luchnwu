package com.tedu.birdnest.portal.mapper;

import com.tedu.birdnest.portal.model.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {

    @Select("SELECT * FROM account WHERE username=#{username}")
    Account findAccountByUsername(String username);

}
