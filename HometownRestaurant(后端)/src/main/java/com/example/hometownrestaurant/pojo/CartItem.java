package com.example.hometownrestaurant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private int cartitemid;
    private int cartid;
    private Integer dishid;
    private int quantity;
    private LocalDateTime addedtime;
}
