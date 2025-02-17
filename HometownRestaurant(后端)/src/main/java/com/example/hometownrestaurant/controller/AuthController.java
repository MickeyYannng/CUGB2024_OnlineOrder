package com.example.hometownrestaurant.controller;

//import com.example.hometownrestaurant.service.AuthService;
//import com.example.hometownrestaurant.service.impl.AuthServiceImpl;
import com.example.hometownrestaurant.pojo.Result;
import com.example.hometownrestaurant.pojo.User;
import com.example.hometownrestaurant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//
@RestController
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        log.info("用户登录:{}", user);
        User u = userService.login(user);
        return u!=null? Result.success():Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        log.info("用户注册：:{}", user);
        userService.register(user);
        return Result.success();
    }
}
