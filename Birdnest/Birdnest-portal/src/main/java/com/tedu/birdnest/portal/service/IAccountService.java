package com.tedu.birdnest.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tedu.birdnest.portal.model.Account;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
public interface IAccountService extends IService<Account> {

    UserDetails getUserDetails(String username);

    String currentUsername();

}
