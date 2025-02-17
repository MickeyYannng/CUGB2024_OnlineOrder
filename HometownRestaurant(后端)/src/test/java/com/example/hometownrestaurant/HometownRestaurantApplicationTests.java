package com.example.hometownrestaurant;

import com.example.hometownrestaurant.mapper.DishMapper;
import com.example.hometownrestaurant.pojo.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

class HometownRestaurantApplicationTests {

    @Autowired
    private DishMapper dishMappe;
    @Test
    public void testdish(){
        System.out.println(dishMappe.selectDishById(1));
    }
//    @Test
//    public void testListDish(){
//        List<Dish> dishList=dishMappe.selectDish();
//       System.out.println(dishList);
//    }
}
