package com.example.hometownrestaurant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long orderid;
    private int userid;
    private double totalmoney;
    private LocalDateTime createtime;
    /**
     * 0 - 未支付 1 - 已支付
     */
    private int status;
    private String paymentmethod;
    private Long interfaceinfoid;
    private int isdelete;
}

