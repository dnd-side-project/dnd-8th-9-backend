package com.team9ookie.dangdo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseDto;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.dto.store.StoreConditionDto;
import com.team9ookie.dangdo.dto.store.StoreListResponseDto;
import com.team9ookie.dangdo.dto.store.StoreRequestDto;
import com.team9ookie.dangdo.dto.store.StoreResponseDto;
import com.team9ookie.dangdo.service.MenuService;
import com.team9ookie.dangdo.service.ReviewService;
import com.team9ookie.dangdo.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final MenuService menuService;
    private final ReviewService reviewService;
    private final ObjectMapper objectMapper;

    @GetMapping("")
    public ResponseEntity<BaseResponseDto<List<StoreListResponseDto>>> findAll(@RequestParam(name = "cond", required = false) String condition) {
        try {
            StoreConditionDto conditionDto;
            if (condition == null) {
                conditionDto = new StoreConditionDto();
            } else {
                conditionDto = objectMapper.readValue(condition, StoreConditionDto.class);
            }
            return ResponseEntity.ok(BaseResponseDto.ok(storeService.findAll(conditionDto)));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("유효하지 않은 필터링 형식 cond=" + condition);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<StoreResponseDto>> findById(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.findById(id)));
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> create(@ModelAttribute StoreRequestDto dto) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.create(dto)));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<StoreResponseDto>> update(@PathVariable long id, @ModelAttribute StoreRequestDto dto) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.delete(id)));
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<BaseResponseDto<List<MenuResponseDto>>> findMenuList(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.findByStoreId(id)));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<BaseResponseDto<List<ReviewResponseDto>>> findReviewList(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.findByStoreId(id)));
    }

}
