package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.menu.MenuRequestDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseDto;
import com.team9ookie.dangdo.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/")
    public List<MenuResponseDto> findAll(@PathVariable Long storeId) {
        return menuService.findAll(storeId);
    }

    @GetMapping("/{menuId}")
    public MenuResponseDto findById(@PathVariable Long menuId) {
        return menuService.findById(menuId);
    }

    @PostMapping("/")
    public Long save(@PathVariable Long storeId, @RequestBody MenuRequestDto requestDto, @RequestPart("menuImg") MultipartFile menuImg) {
        return menuService.save(requestDto, storeId, menuImg);
    }

    @PutMapping("/{menuId}")
    public Long update(@PathVariable Long storeId, @PathVariable Long menuId, @RequestPart MenuRequestDto requestDto) {
        return menuService.update(menuId, storeId, requestDto);
    }

    @DeleteMapping("/{menuId}")
    public Long delete(@PathVariable Long menuId) {
        menuService.delete(menuId);
        return menuId;
    }
}