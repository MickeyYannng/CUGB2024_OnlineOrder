package com.example.hometownrestaurant.pojo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有的请求路径应用CORS配置
                .allowedOrigins("*") // 允许指定域的跨域请求
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
                .allowedHeaders("*") // 允许的头信息
//                .allowCredentials(true) // 是否允许证书（cookies）
                .maxAge(3600); // 预检请求的缓存时间（秒）
    }
}