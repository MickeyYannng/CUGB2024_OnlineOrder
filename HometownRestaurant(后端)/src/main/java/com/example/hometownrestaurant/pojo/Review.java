package com.example.hometownrestaurant.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int reviewid;
    private int userid;
    private int dishid;
    private int rating;
    private String comment;
    private LocalDateTime createdtime;
}
