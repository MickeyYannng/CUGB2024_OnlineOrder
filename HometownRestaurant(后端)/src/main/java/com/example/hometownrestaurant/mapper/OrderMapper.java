package com.example.hometownrestaurant.mapper;

import com.example.hometownrestaurant.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Select("Select * from [orders] where orderid=#{id}")
    Order selectOrderById(long id);

    @Select("select  * from [orders]")
    List<Order> getAllOrders();
//      @Insert("INSERT INTO dish (name, description, price) VALUES (#{name}, #{description}, #{price})")
    @Insert("insert into [orders] values (#{userid},#{totalmoney},#{createtime},#{status},#{paymentmethod}," +
            "#{interfaceinfoid},#{isdelete})")
    @Options(useGeneratedKeys = true, keyProperty = "orderid")
    void createOrder(Order order);
}
