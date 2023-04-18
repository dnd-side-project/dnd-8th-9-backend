package com.team9ookie.dangdo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.menu.*;
import com.team9ookie.dangdo.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final ObjectMapper objectMapper;

    @GetMapping("/all")
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

    @GetMapping("")
    public ResponseEntity<BaseResponseDto<List<MenuResponseDto>>> findAllByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.findAllByStoreId(storeId)));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<BaseResponseDto<MenuResponseDto>> findById(@PathVariable Long menuId) {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.findById(menuId)));
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> save(@PathVariable Long storeId, @RequestPart MenuRequestDto dto, @RequestPart(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.save(dto, storeId, fileList)));
    }

    @PutMapping(value = "/{menuId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> update(@PathVariable Long storeId, @PathVariable Long menuId, @RequestPart MenuRequestDto dto, @RequestPart(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.update(menuId, storeId, dto, fileList)));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable Long menuId) {
        return ResponseEntity.ok(BaseResponseDto.ok(menuService.delete(menuId)));
    }
}
