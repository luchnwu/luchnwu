package com.tedu.straw.portal.security;

import com.tedu.straw.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserDetails(username);
    }


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails user = null;
//        if ("Jerry".equals(username)) {
//            user = User.builder()
//                    .username("Jerry")
//                    .password("{bcrypt}$2a$10$OQ2U4CS9RylvIFVtKrxExuwwSAEVRlhNAhzjOIpf9lNLQO8912DuC")
//                    .authorities("/user/get","/user/list")
//                    .build();
//        }
//        System.out.println("user:"+user);
//        return user;
//    }
}
