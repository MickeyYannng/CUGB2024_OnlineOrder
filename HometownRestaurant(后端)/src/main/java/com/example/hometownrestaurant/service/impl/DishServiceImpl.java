package com.example.hometownrestaurant.service.impl;

import com.example.hometownrestaurant.mapper.DishMapper;
import com.example.hometownrestaurant.pojo.Dish;
import com.example.hometownrestaurant.service.DishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Override
    public List<Dish> list() {
        return dishMapper.list();
    }

    @Override
    public void delete(int id) {
        dishMapper.deleteDish(id);
    }

    @Override
    public void adddish(Dish dish) {
        dishMapper.insertDish(dish);
    }

    @Override
    public Dish getDishById(int id) {
        return dishMapper.selectDishById(id);
    }

    @Override
    public void updateDish(Dish dish) {
        dishMapper.updateDish(dish);
    }

    @Override
    public Dish getDishByName(String name) {
        return dishMapper.selectDishByName(name);
    }

    public PageInfo<Dish> findByPage(Integer page, Integer pagesize){
        PageHelper.startPage(page, pagesize);
       return new PageInfo<> (dishMapper.list());
    }
}
