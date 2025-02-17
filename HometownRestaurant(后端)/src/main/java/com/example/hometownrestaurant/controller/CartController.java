package com.example.hometownrestaurant.controller;

import com.example.hometownrestaurant.pojo.CartItem;
import com.example.hometownrestaurant.pojo.Result;
import com.example.hometownrestaurant.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping("/items")
    public Result getCartItemsByUserId(@RequestParam int userid) {
        log.info("查看购物车:{}", userid);
        List<CartItem>cartItems=cartService.getCartItemsByUserId(userid);
        return Result.success(cartItems);
    }
    @PostMapping("/add")
    public Result addToCart(@RequestParam int userid, @RequestParam int dishid) {
        log.info("添加到购物车：参数{},{}",userid,dishid);
        cartService.addToCart(userid, dishid);
        return Result.success();
    }
    @DeleteMapping("/items/{cartitemid}")
    public Result removeCartItem(@PathVariable int cartitemid) {
        log.info("从购物车中删除:{}",cartitemid);
        cartService.removeCartItem(cartitemid);
        return Result.success();
    }
}
