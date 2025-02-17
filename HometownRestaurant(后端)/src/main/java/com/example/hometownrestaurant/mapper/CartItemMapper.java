package com.example.hometownrestaurant.mapper;

import com.example.hometownrestaurant.pojo.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartItemMapper {

    @Insert("INSERT INTO cartItems(cartid, dishid, quantity, addedtime) " +
            "VALUES (#{cartid}, #{dishid}, #{quantity}, #{addedtime})")
    @Options(useGeneratedKeys = true, keyProperty = "cartitemid")
    void addCartItem(CartItem cartItem);

    @Update("UPDATE cartItems SET quantity = #{quantity} WHERE cartid = #{cartid} AND dishid = #{dishid}")
    void updateCartItemQuantity(@Param("cartid")int cartid,@Param("dishid")  int dishid,@Param("quantity")  int quantity);

    @Select("SELECT * FROM cartItems WHERE cartid = #{cartid} AND dishid = #{dishid}")
    CartItem findCartItemByCartidAndDishid(@Param("cartid") int cart_id,@Param("dishid") int dishid);

    @Select("SELECT * FROM cartItems WHERE cartid = #{cartid}")
    List<CartItem> findAllByCartId(@Param("cartid") int cartid);

    @Delete("DELETE FROM cartItems WHERE cartitemid = #{cartitemid}")
    void deleteCartItemById(int cartitemid);

}
