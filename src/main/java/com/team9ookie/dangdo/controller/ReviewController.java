package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store/{storeId}/menu/{menuId}/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;



}
