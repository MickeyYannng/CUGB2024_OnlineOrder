package com.example.hometownrestaurant.mapper;
import com.example.hometownrestaurant.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 插入用户
    @Insert("INSERT INTO users (username, password, phone, email, address, createdate) VALUES (#{username},#{password}," +
            "#{phone},#{email},#{address},#{createdate} )")
    void insertUser(User user);

    // 根据用户名查找用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    // 根据ID查找用户
    @Select("SELECT * FROM users WHERE userid = #{id}")
    User findById(int id);

    // 获取所有用户
    @Select("SELECT * FROM users")
    List<User> findAllUsers();

    // 更新用户信息
    @Update("UPDATE users SET username = #{username}, password = #{password} ,phone=#{phone},email=#{email}," +
            "address=#{address},createdate=#{createdate} where userid = #{userid}")
    void updateUser(User user);

    // 删除用户
    @Delete("DELETE FROM users WHERE userid = #{id}")
    void deleteUser(int id);

    //检查用户是否存在
    @Select("select * from users where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(User user);
}
