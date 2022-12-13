package com.tedu.birdnest.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tedu.birdnest.portal.mapper.AccountMapper;
import com.tedu.birdnest.portal.model.Account;
import com.tedu.birdnest.portal.service.IAccountService;
import com.tedu.birdnest.portal.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.com
 * @since 2022-11-07
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public UserDetails getUserDetails(String username) {

        Account account = accountMapper.findAccountByUsername(username);
        if (account == null) {
            return null;
        }

        UserDetails user = User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .authorities("/index.html")
                .build();

        return user;
    }

    @Override
    public String currentUsername() {
        //利用安全框架API取得當前登陸用戶資訊
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //檢查是否匿名登陸狀態 匿名登陸就是沒登錄
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return username;
        }
        throw ServiceException.unprocessableEntity("尚未登陸");
    }


}
