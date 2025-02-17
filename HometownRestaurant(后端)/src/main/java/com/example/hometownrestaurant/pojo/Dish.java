package com.example.hometownrestaurant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private int dishid;
    private String name;
    private double price;
    private String description;
    private String img;
}
