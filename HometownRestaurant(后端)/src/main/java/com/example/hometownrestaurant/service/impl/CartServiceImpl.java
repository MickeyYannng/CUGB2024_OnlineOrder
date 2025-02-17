package com.example.hometownrestaurant.service.impl;

import com.example.hometownrestaurant.mapper.CartItemMapper;
import com.example.hometownrestaurant.mapper.CartMapper;
import com.example.hometownrestaurant.pojo.CartItem;
import com.example.hometownrestaurant.pojo.Dish;
import com.example.hometownrestaurant.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public List<CartItem> getCartItemsByUserId(int userid) {
        int cartid = cartMapper.findCartIdByUserId(userid);
        return cartItemMapper.findAllByCartId(cartid);
    }

    @Override
    public void removeCartItem(int cartitemid) {
        cartItemMapper.deleteCartItemById(cartitemid);
    }

    @Override
    public void addToCart(int userid, int dishid) {
        int cartid = cartMapper.findCartIdByUserId(userid);
//        log.info("cartid: " + cartid);
        CartItem existingItem = cartItemMapper.findCartItemByCartidAndDishid(cartid, dishid);
//        log.info("existingItem: " + existingItem);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + 1;
            cartItemMapper.updateCartItemQuantity(cartid, dishid, newQuantity);
        } else {
            CartItem newItem = new CartItem();

            newItem.setCartid(cartid);
            newItem.setDishid(dishid);
            newItem.setQuantity(1);
            newItem.setAddedtime(LocalDateTime.now());
            log.info("new item: " + newItem);
            cartItemMapper.addCartItem(newItem);
        }
    }
}
