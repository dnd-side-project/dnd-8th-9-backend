package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.review.ReviewRequestDto;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/menus/{menuId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<BaseResponseDto<List<ReviewResponseDto>>> getAll(@PathVariable long menuId) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.getAll(menuId)));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<BaseResponseDto<ReviewResponseDto>> get(@PathVariable long reviewId) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.get(reviewId)));
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> create(@PathVariable long menuId, @ModelAttribute ReviewRequestDto dto) throws IOException {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.create(menuId, dto)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable long reviewId) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.delete(reviewId)));
    }

}
