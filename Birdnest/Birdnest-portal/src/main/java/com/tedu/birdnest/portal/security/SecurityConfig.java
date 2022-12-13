package com.tedu.birdnest.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    //資源訪問範圍
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //關閉跨域防範 避免古怪錯誤
        http.csrf().disable()
                //提供請求授權
                .authorizeRequests()
                //不須認證可訪問資源
                .antMatchers(
                        "/",
                        "/index.html",
                        "/login.html",
                        "/register.html",
                        "/item.html",
                        "/item-list.html",
                        "/portal/product/*",
                        "/register",
                        "/bootstrap5/**",
                        "/img/**",
                        "/css/*",
                        "/js/*",
                        "/jquery/*",
                        "/vue/**")
                .permitAll()
                //需認證訪問資源採表單認證
                .anyRequest()
                .authenticated()
                .and().formLogin()
                //設置登陸頁面及安全過濾器自動進行登錄處理的url
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error")
                //登陸成功後自動定向頁面
                .defaultSuccessUrl("/index.html")
                .and()
                //設置登出功能及安全過濾器自動進行登出處理的url 最後重定向回首頁
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html");

//        super.configure(http);
    }

    //帳戶安全認證
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //安全配置使用者認證接口
        auth.userDetailsService(userDetailsService);

//      //安全配置指定使用者
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{bcrypt}$2a$10$a1pndBenauhDr4Gh6IdqceF5/.1oJV7qT0/qWyRu1xsiIts5o/Kke")
//                .authorities("/index");

//        super.configure(auth);
    }

//     //密碼配置{bcrypt}可自動匹配加密器 不用設定
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
