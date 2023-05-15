package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
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
    private Long storeId;
    private String storeName;
    private String name;
    private List<FileDto> menuImages;
    private int basePrice;
    private String desc;

    public static class MenuResponseDtoBuilder {}

    public static MenuResponseDto.MenuResponseDtoBuilder create(Menu menu){
        Store store = menu.getStore();
        return MenuResponseDto.builder()
                .id(menu.getId())
                .storeId(store.getId())
                .storeName(store.getName())
                .name(menu.getName())
                .basePrice(menu.getPrice())
                .desc(menu.getDescription());
    }
}
