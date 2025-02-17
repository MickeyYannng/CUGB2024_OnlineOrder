package com.example.hometownrestaurant.service;

import com.example.hometownrestaurant.pojo.Dish;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DishService {
    List<Dish> list();
    void delete(int id);

    void adddish(Dish dish);

    Dish getDishById(int id);

    void updateDish(Dish dish);

    Dish getDishByName(String name);

    PageInfo<Dish> findByPage(Integer page, Integer pagesize);
}
