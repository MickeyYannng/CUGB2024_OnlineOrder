package com.example.hometownrestaurant.mapper;

import com.example.hometownrestaurant.pojo.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface ReviewMapper {
    @Select("SELECT * FROM reviews WHERE userid = #{userid}")
    List<Review> findReviewsByUserId(@Param("userid") int userid);
}
