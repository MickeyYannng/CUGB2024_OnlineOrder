package com.example.hometownrestaurant.controller;

import com.example.hometownrestaurant.pojo.Review;
import com.example.hometownrestaurant.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/user/{userid}")
    public List<Review> getReviewsByUserId(@PathVariable int userid) {
        return reviewService.getReviewsByUserId(userid);
    }
}
