package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.menu.MenuConditionDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseListDto;
import com.team9ookie.dangdo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuBookmarkService {

    private final MenuService menuService;

    public List<MenuResponseListDto> findMarkedMenuList(User user) {
        MenuConditionDto conditionDto = MenuConditionDto.builder()
                .userId(user.getId())
                .build();
        return menuService.findAll(conditionDto);
    }

}
