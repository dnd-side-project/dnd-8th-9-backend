package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.StoreDto;
import com.team9ookie.dangdo.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> get(@PathVariable long id) {
        return ResponseEntity.ok(storeService.get(id));
    }

}
