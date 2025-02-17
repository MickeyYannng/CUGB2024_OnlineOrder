package com.example.hometownrestaurant.service;

import com.example.hometownrestaurant.pojo.Dish;
import com.example.hometownrestaurant.pojo.User;

import java.util.List;

public interface UserService {
    List<User> list();

    void delete(int id);

    void adduser(User user);

    User getUserById(int id);

    void updateUser(User user);

    User getUserByName(String name);

    User login(User user);

    void register(User user);
}


