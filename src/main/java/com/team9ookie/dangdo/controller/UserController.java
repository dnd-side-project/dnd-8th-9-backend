package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.auth.userinfo.PrincipalDetails;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseListDto;
import com.team9ookie.dangdo.dto.store.StoreListResponseDto;
import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.service.MenuBookmarkService;
import com.team9ookie.dangdo.service.StoreBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final StoreBookmarkService storeBookmarkService;
    private final MenuBookmarkService menuBookmarkService;

    @GetMapping("/store-bookmarks")
    public ResponseEntity<BaseResponseDto<List<StoreListResponseDto>>> findStoreBookmarkList(Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        User user = ((PrincipalDetails) ((OAuth2AuthenticationToken) principal).getPrincipal()).getUser();
        return ResponseEntity.ok(BaseResponseDto.ok(storeBookmarkService.findMarkedStoreList(user)));
    }

    @GetMapping("/menu-bookmarks")
    public ResponseEntity<BaseResponseDto<List<MenuResponseListDto>>> findMenuBookmarkList(Principal principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        User user = ((PrincipalDetails) ((OAuth2AuthenticationToken) principal).getPrincipal()).getUser();
        return ResponseEntity.ok(BaseResponseDto.ok(menuBookmarkService.findMarkedMenuList(user)));
    }

}
