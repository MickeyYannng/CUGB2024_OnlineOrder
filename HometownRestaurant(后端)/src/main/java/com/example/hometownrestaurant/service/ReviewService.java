package com.example.hometownrestaurant.service;

import com.example.hometownrestaurant.pojo.Review;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReviewService {

    List<Review> getReviewsByUserId(int userid);
}
