package com.example.hometownrestaurant.service.impl;

import com.example.hometownrestaurant.mapper.ReviewMapper;
import com.example.hometownrestaurant.pojo.Review;
import com.example.hometownrestaurant.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;

    public List<Review> getReviewsByUserId(int userid) {
        return reviewMapper.findReviewsByUserId(userid);
    }
}
