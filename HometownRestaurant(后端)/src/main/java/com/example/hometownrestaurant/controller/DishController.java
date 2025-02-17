package com.example.hometownrestaurant.controller;

import com.example.hometownrestaurant.pojo.Dish;
import com.example.hometownrestaurant.pojo.Result;
import com.example.hometownrestaurant.service.DishService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dishes")
public class DishController {
    @Autowired
    private DishService dishService;

    @GetMapping
    public Result listDishes(){
        log.info("查询全部菜品");
        List<Dish> dishList=dishService.list();
        return Result.success(dishList);
    }
    @GetMapping("/bypage")
    public Result findByPage(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer pagesize) {
        log.info("分页查询，参数:{}，{}",page,pagesize);
        PageInfo<Dish> pageInfo = dishService.findByPage(page, pagesize);
        return Result.success(pageInfo);
    }

    @DeleteMapping("/{id}")
    public Result deleteDish(@PathVariable int id){
        log.info("根据id删除菜品:{}",id);
        dishService.delete(id);
        return Result.success();
    }
    @PostMapping
    public Result addDish(@RequestBody Dish dish){
        log.info("新增菜品:{}",dish);
        dishService.adddish(dish);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result getDishById(@PathVariable int id){
        log.info("根据id查菜品:{}",id);
        Dish dish=dishService.getDishById(id);
        log.info(dish.toString());
        return Result.success(dish);
    }
    @GetMapping("/byname/{name}")
    public Result getDishById(@PathVariable String name){
        log.info("根据菜名查菜品:{}",name);
        Dish dish=dishService.getDishByName(name);
        return Result.success(dish);
    }
    @PutMapping
    public Result updateDish(@RequestBody Dish dish){
        log.info("更新菜品信息:{}",dish);
        dishService.updateDish(dish);
        return Result.success();
    }
}
