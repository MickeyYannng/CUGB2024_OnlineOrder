package com.example.hometownrestaurant.controller;

import com.example.hometownrestaurant.pojo.Dish;
import com.example.hometownrestaurant.pojo.Order;
import com.example.hometownrestaurant.pojo.Result;
import com.example.hometownrestaurant.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result listOrders(){
        log.info("查询全部订单:");
        List<Order> orderList=orderService.list();
        return Result.success(orderList);
    }

    @GetMapping("/{id}")
    public Result order(@PathVariable long id) {
        log.info("根据id查订单:{}",id);
        Order order =orderService.getOrderById(id);
        return Result.success(order);
    }

}
