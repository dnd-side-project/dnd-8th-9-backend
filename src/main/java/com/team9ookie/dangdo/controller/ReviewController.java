package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.review.ReviewRequestDto;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("")
    public ResponseEntity<BaseResponseDto<List<ReviewResponseDto>>> findAll() {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<ReviewResponseDto>> findById(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.findById(id)));
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> create(@ModelAttribute ReviewRequestDto dto) throws IOException {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.create(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.delete(id)));
    }

}
