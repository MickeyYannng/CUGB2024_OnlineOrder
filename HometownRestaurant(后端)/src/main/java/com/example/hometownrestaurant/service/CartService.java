package com.example.hometownrestaurant.service;

import com.example.hometownrestaurant.pojo.CartItem;
import com.example.hometownrestaurant.pojo.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    void addToCart(int userid, int dishid);

    List<CartItem> getCartItemsByUserId(int userid);

    void removeCartItem(int cartitemid);
}
