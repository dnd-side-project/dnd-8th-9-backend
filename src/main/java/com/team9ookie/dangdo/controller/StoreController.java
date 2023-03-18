package com.team9ookie.dangdo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team9ookie.dangdo.dto.BaseResponseDto;
import com.team9ookie.dangdo.dto.store.StoreConditionDto;
import com.team9ookie.dangdo.dto.store.StoreListResponseDto;
import com.team9ookie.dangdo.dto.store.StoreRequestDto;
import com.team9ookie.dangdo.dto.store.StoreResponseDto;
import com.team9ookie.dangdo.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    private final ObjectMapper objectMapper;

    @GetMapping()
    public ResponseEntity<BaseResponseDto<List<StoreListResponseDto>>> getAll(@RequestParam(name = "cond", required = false) String condition) throws JsonProcessingException {
        try {
            StoreConditionDto conditionDto;
            if (condition == null) {
                conditionDto = new StoreConditionDto();
            } else {
                conditionDto = objectMapper.readValue(condition, StoreConditionDto.class);
            }
            return ResponseEntity.ok(BaseResponseDto.ok(storeService.getAll(conditionDto)));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("유효하지 않은 필터링 형식 cond=" + condition);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDto<StoreResponseDto>> get(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.get(id)));
    }

    @GetMapping(params = "name")
    public ResponseEntity<BaseResponseDto<List<StoreResponseDto>>> searchStoresByName(@RequestParam String name) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.searchStoresByName(name)));
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<Long>> create(@ModelAttribute StoreRequestDto dto, @RequestParam(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.create(dto, fileList)));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BaseResponseDto<StoreResponseDto>> update(@PathVariable long id, @ModelAttribute StoreRequestDto dto, @RequestParam(name = "files", required = false) List<MultipartFile> fileList) throws Exception {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.update(id, dto, fileList)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDto<Long>> delete(@PathVariable long id) {
        return ResponseEntity.ok(BaseResponseDto.ok(storeService.delete(id)));
    }

}
