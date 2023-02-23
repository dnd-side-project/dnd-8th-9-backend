package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.menu.MenuDetailDto;
import com.team9ookie.dangdo.dto.menu.MenuRequestDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseDto;
import com.team9ookie.dangdo.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/store/{storeId}/menu")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("")
    public List<MenuResponseDto> findAll(@PathVariable Long storeId) {
        return menuService.findAll(storeId);
    }

    @GetMapping("/{menuId}")
    public MenuDetailDto findById(@PathVariable Long menuId) {
        return menuService.findById(menuId);
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@PathVariable Long storeId, @RequestPart MenuRequestDto requestDto, @RequestPart MultipartFile menuImg) {
        return menuService.save(requestDto, storeId, menuImg);
    }

    @PutMapping(value = "/{menuId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long storeId, @PathVariable Long menuId, @RequestPart MenuRequestDto requestDto, @RequestPart MultipartFile menuImg) {
        return menuService.update(menuId, storeId, requestDto, menuImg);
    }

    @DeleteMapping("/{menuId}")
    public Long delete(@PathVariable Long menuId) {
        menuService.delete(menuId);
        return menuId;
    }
}
