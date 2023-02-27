package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {

    private Long id;
    private String name;
    private String summary;
    private int price;
    private List<FileDto> menuImages;

    public static MenuResponseDtoBuilder create(Menu menu){
        return MenuResponseDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .summary(menu.getSummary());
    }
}
