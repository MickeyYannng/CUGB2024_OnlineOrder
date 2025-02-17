package com.example.hometownrestaurant.service.impl;

import com.example.hometownrestaurant.mapper.OrderMapper;
import com.example.hometownrestaurant.pojo.Order;
import com.example.hometownrestaurant.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public Order getOrderById(long orderid) {
        return orderMapper.selectOrderById(orderid);
    }

    @Override
    public List<Order> list() {
        return orderMapper.getAllOrders();
    }

    @Override
    public Order createOrder(int userid,double totalmoney) {
        Order order = new Order();
        order.setInterfaceinfoid(294389472L);  // 接口信息ID
        order.setPaymentmethod("支付宝");  // 支付方式
        order.setUserid(userid);
        order.setTotalmoney(totalmoney);
        order.setStatus(0);
        order.setIsdelete(0);
        order.setCreatetime(LocalDateTime.now());
        orderMapper.createOrder(order);
        return orderMapper.selectOrderById(order.getOrderid());
    }
}
