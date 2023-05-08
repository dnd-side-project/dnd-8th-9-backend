package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetailDto {
    private Long id;
    private Long storeId;
    private String storeName;
    private String name;
    private int basePrice;
    private List<String> category;
    private List<FileDto> menuImages;
    private Map<String, String> basicInfo;
    private String detailInfo;

    public static class MenuDetailDtoBuilder {}

    public static MenuDetailDto.MenuDetailDtoBuilder create(Menu menu){
        return MenuDetailDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .basePrice(menu.getPrice())
                .category(Arrays.stream(menu.getCategory().split(",")).toList())
                .basicInfo(menu.getBasicInfo())
                .detailInfo(menu.getDetailInfo());
    }
}
