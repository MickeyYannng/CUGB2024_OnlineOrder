package com.example.hometownrestaurant.controller;

import com.example.hometownrestaurant.pojo.User;
import com.example.hometownrestaurant.pojo.Result;
import com.example.hometownrestaurant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Result listUseres(){
        log.info("查询全部用户");
        List<User> userList=userService.list();
        return Result.success(userList);
    }
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable int id){
        log.info("根据id删除用户:{}",id);
        userService.delete(id);
        return Result.success();
    }
    @PostMapping
    public Result addUser(@RequestBody User user){
        log.info("新增用户:{}",user);
        userService.adduser(user);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable int id ){
        log.info("根据id查用户:{}",id);
        User user=userService.getUserById(id);
        return Result.success(user);
    }
    @GetMapping("/byname/{name}")
    public Result getUserByName(@PathVariable String name){
        log.info("根据用户名查用户:{}",name);
        User user=userService.getUserByName(name);
        return Result.success(user);
    }
    @PutMapping
    public Result updateUser(@RequestBody User user){
        log.info("更新用户信息:{}",user);
        userService.updateUser(user);
        return Result.success();
    }
}