package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {

    private Long menuId;
    private String name;
    private int price;
    private String caution;
    private String description;
    private List<String> category;

    public static MenuResponseDto of(Menu menu){
        return MenuResponseDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .caution(menu.getCaution())
                .category(new ArrayList<>(Arrays.asList(menu.getCategory().split(","))))
                .description(menu.getDescription())
                .build();
    }
}
