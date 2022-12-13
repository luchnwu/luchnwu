package com.tedu.birdnest.portal.security;

import com.tedu.birdnest.portal.service.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IAccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //使用數據庫使用者資料
        return accountService.getUserDetails(username);

//        //指定使用者配置
//        UserDetails user = null;
//        if ("admin".equals(username)){
//            user = User.builder()
//                    .username("admin")
//                    .password("{bcrypt}$2a$10$a1pndBenauhDr4Gh6IdqceF5/.1oJV7qT0/qWyRu1xsiIts5o/Kke")
//                    .authorities("/index")
//                    .build();
//        }
//
//        log.debug("user:{}",user);
//
//        return user;
    }
}
