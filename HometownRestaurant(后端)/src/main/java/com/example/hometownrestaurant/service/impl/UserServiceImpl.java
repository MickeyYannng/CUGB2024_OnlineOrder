package com.example.hometownrestaurant.service.impl;

import com.example.hometownrestaurant.mapper.UserMapper;
import com.example.hometownrestaurant.pojo.User;
import com.example.hometownrestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> list() {
        return userMapper.findAllUsers();
    }
    @Override
    public void delete(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void adduser(User user) {
        user.setCreatedate(LocalDateTime.now());
        userMapper.insertUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.findById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.findByUsername(name);
    }

    @Override
    public User login(User user) {
        return userMapper.findByUsernameAndPassword(user);
    }

    @Override
    public void register(User user) {
        userMapper.insertUser(user);
    }
}
