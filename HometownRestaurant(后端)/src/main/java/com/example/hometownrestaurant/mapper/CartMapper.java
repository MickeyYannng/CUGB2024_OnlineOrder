package com.example.hometownrestaurant.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartMapper {
    @Select("SELECT cartid FROM carts WHERE userid = #{userid}")
    int findCartIdByUserId(int userid);
}

