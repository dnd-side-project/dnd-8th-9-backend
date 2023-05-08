package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.auth.common.ApiResponse;
import com.team9ookie.dangdo.config.AppProperties;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseListDto;
import com.team9ookie.dangdo.dto.store.StoreListResponseDto;
import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.service.MenuBookmarkService;
import com.team9ookie.dangdo.service.StoreBookmarkService;
import com.team9ookie.dangdo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final StoreBookmarkService storeBookmarkService;
    private final MenuBookmarkService menuBookmarkService;

    @GetMapping
    public ResponseEntity<BaseResponseDto<User>> getUser() {

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        return ResponseEntity.ok(BaseResponseDto.ok(user));
    }

    @GetMapping("/store-bookmarks")
    public ResponseEntity<BaseResponseDto<List<StoreListResponseDto>>> findStoreBookmarkList() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        User user = userService.getUser(principal.getUsername());

        return ResponseEntity.ok(BaseResponseDto.ok(storeBookmarkService.findMarkedStoreList(user)));
    }

    @GetMapping("/menu-bookmarks")
    public ResponseEntity<BaseResponseDto<List<MenuResponseListDto>>> findMenuBookmarkList() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        User user = userService.getUser(principal.getUsername());

        return ResponseEntity.ok(BaseResponseDto.ok(menuBookmarkService.findMarkedMenuList(user)));
    }
}
