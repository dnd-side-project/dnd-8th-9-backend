package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.dto.file.FileDto;
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
public class MenuDetailDto {

    private Long id;
    private String name;
    private int price;
    private String caution;
    private String description;
    private List<String> category;
    private List<FileDto> menuImages;

    public static MenuDetailDtoBuilder create(Menu menu){
        return MenuDetailDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .caution(menu.getCaution())
                .category(new ArrayList<>(Arrays.asList(menu.getCategory().split(","))))
                .description(menu.getDescription());
    }

}
