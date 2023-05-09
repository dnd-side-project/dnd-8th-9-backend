package com.team9ookie.dangdo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.menu.MenuConditionDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseListDto;
import com.team9ookie.dangdo.dto.store.StoreConditionDto;
import com.team9ookie.dangdo.dto.store.StoreListResponseDto;
import com.team9ookie.dangdo.dto.user.UserResponseDto;
import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.service.MenuService;
import com.team9ookie.dangdo.service.StoreService;
import com.team9ookie.dangdo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final StoreService storeService;
    private final MenuService menuService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<BaseResponseDto<UserResponseDto>> getUser() {

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        return ResponseEntity.ok(BaseResponseDto.ok(UserResponseDto.create(user).build()));
    }

    @GetMapping("/store-bookmarks")
    public ResponseEntity<BaseResponseDto<List<StoreListResponseDto>>> findStoreBookmarkList(@RequestParam(name = "cond", required = false) String condition) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            StoreConditionDto conditionDto;
            if (condition == null) {
                conditionDto = new StoreConditionDto();
            } else {
                conditionDto = objectMapper.readValue(condition, StoreConditionDto.class);
            }
            User user = userService.getUser(principal.getUsername());
            conditionDto.setUserId(user.getId());
            return ResponseEntity.ok(BaseResponseDto.ok(storeService.findAll(conditionDto)));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("유효하지 않은 필터링 형식 cond=" + condition);
        }
    }

    @GetMapping("/menu-bookmarks")
    public ResponseEntity<BaseResponseDto<List<MenuResponseListDto>>> findMenuBookmarkList(@RequestParam(name = "cond", required = false) String condition) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            MenuConditionDto conditionDto;
            if (condition == null) {
                conditionDto = new MenuConditionDto();
            } else {
                conditionDto = objectMapper.readValue(condition, MenuConditionDto.class);
            }
            User user = userService.getUser(principal.getUsername());
            conditionDto.setUserId(user.getId());
            return ResponseEntity.ok(BaseResponseDto.ok(menuService.findAll(conditionDto)));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("유효하지 않은 필터링 형식 cond=" + condition);
        }
    }
}
