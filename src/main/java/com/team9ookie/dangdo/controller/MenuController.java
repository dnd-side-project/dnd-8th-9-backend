package com.team9ookie.dangdo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.menu.*;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.service.MenuService;
import com.team9ookie.dangdo.service.ReviewService;
import com.team9ookie.dangdo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("")
    public ResponseEntity<BaseResponseDto<List<MenuResponseListDto>>> findAll(@RequestParam(name = "cond", required = false) String condition) {
        try {
            MenuConditionDto conditionDto;
            if (condition == null) {
                conditionDto = new MenuConditionDto();
            } else {
                conditionDto = objectMapper.readValue(condition, MenuConditionDto.class);
            }
            return ResponseEntity.ok(BaseResponseDto.ok(menuService.findAll(conditionDto)));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("유효하지 않은 필터링 형식 cond=" + condition);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<MenuDetailDto>> findById(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.findById(id)));
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> save(@RequestPart MenuRequestDto dto, @RequestPart(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.save(dto, fileList)));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> update(@PathVariable long id, @RequestPart MenuRequestDto dto, @RequestPart(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.update(id, dto, fileList)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.delete(id)));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<BaseResponseDto<List<ReviewResponseDto>>> findReviewList(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(reviewService.findByMenuId(id)));
    }

    @PostMapping("/{id}/bookmarks")
    public ResponseEntity<BaseResponseDto<Long>> createBookmark(@PathVariable long id) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.createBookmark(id, user)));
    }

    @DeleteMapping("/{id}/bookmarks")
    public ResponseEntity<BaseResponseDto<Long>> deleteBookmark(@PathVariable long id) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.deleteBookmark(id, user)));
    }

}
