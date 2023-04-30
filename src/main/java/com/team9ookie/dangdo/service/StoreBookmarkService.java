package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.store.StoreConditionDto;
import com.team9ookie.dangdo.dto.store.StoreListResponseDto;
import com.team9ookie.dangdo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreBookmarkService {

    private final StoreService storeService;

    public List<StoreListResponseDto> findMarkedStoreList(User user) {
        StoreConditionDto conditionDto = StoreConditionDto.builder()
                .userId(user.getId())
                .build();
        return storeService.findAll(conditionDto);
    }

}
