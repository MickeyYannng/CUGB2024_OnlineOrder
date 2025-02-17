package com.example.hometownrestaurant.service;

import com.example.hometownrestaurant.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {


    Order getOrderById(long id);

    List<Order> list();

    Order createOrder(int userid, double totalmoney);
}
