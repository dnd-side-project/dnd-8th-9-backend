package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.auth.common.ApiResponse;
import com.team9ookie.dangdo.config.AppProperties;
import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AppProperties appProperties;
    @GetMapping
    public ApiResponse getUser() {
        System.out.println(appProperties.getAuth().getTokenSecret());
        System.out.println(appProperties.getAuth().getTokenExpiry());
        System.out.println(appProperties.getAuth().getRefreshTokenExpiry());

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        return ApiResponse.success("user", user);
    }
}