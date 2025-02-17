package com.example.hometownrestaurant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userid;           // 用户ID
    private String username;   // 用户名
    private String password;   // 加密后的密码
    private String phone;      //电话
    private String email;       //邮箱
    private String address;     //收货地址
    private LocalDateTime createdate;   //注册时间
}



