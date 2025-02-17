package com.example.hometownrestaurant;

import com.example.hometownrestaurant.pojo.AlipayConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//@EnableConfigurationProperties(AlipayConfig.class)
@SpringBootApplication
public class HometownRestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(HometownRestaurantApplication.class, args);
    }

}
