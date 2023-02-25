package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.store.StoreRequestDto;
import com.team9ookie.dangdo.dto.store.StoreResponseDto;
import com.team9ookie.dangdo.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<BaseResponseDto<List<StoreResponseDto>>> getAll() {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<StoreResponseDto>> get(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.get(id)));
    }

    @PostMapping
    public ResponseEntity<BaseResponseDto<Long>> create(@RequestPart StoreRequestDto dto, @RequestParam(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.create(dto, fileList)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDto<StoreResponseDto>> update(@PathVariable long id, @RequestPart StoreRequestDto dto, @RequestParam(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.update(id, dto, fileList)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.delete(id)));
    }

}
