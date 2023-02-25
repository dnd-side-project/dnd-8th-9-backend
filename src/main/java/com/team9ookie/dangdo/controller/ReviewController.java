package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.ReviewRequestDto;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.service.ReviewService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<ReviewResponseDto>> getAll(@PathVariable long menuId) {
        return ResponseEntity.ok(reviewService.getAll(menuId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> get(@PathVariable long reviewId) {
        return ResponseEntity.ok(reviewService.get(reviewId));
    }

    @PostMapping
    public ResponseEntity<Long> create(@PathVariable long menuId, @RequestPart ReviewRequestDto dto, @RequestParam(name = "files", required = false) List<MultipartFile> fileList) throws IOException {
        return ResponseEntity.ok(reviewService.create(menuId, dto, fileList));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> delete(@PathVariable long reviewId) {
        return ResponseEntity.ok(reviewService.delete(reviewId));
    }

}
