package com.example.hometownrestaurant.mapper;
import com.example.hometownrestaurant.pojo.Dish;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface DishMapper {
    @Select("Select * from dish")
    List<Dish> list();

    @Select("SELECT * FROM dish WHERE dishid = #{id}")
    Dish selectDishById(int id);

    @Select("SELECT * FROM dish WHERE name = #{name}")
    Dish selectDishByName(String name);

    @Insert("INSERT INTO dish (name, description, price) VALUES (#{name}, #{description}, #{price})")
    void insertDish(Dish dish);

    @Update("UPDATE dish SET name = #{name}, description = #{description}, price = #{price} ,img=#{img} WHERE dishid = #{dishid}")
    void updateDish(Dish dish);

    @Delete("DELETE FROM dish WHERE dishid = #{id}")
    void deleteDish(int id);

}
