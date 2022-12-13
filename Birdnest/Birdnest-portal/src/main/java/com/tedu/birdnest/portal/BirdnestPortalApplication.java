package com.tedu.birdnest.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tedu.birdnest.portal.mapper")
public class BirdnestPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirdnestPortalApplication.class, args);
    }

}
