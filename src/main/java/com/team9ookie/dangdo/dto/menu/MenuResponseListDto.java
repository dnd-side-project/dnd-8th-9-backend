package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.dto.file.FileDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseListDto {

    private Long id;
    private String name;
    private List<String> category;
    private int price;

    private List<FileDto> menuImages;

    private Long storeId;

    private String storeName;

    private boolean canPickup;

    private boolean canDelivery;

    public static MenuResponseListDtoBuilder create(MenuListDetailDto dto) {
        return MenuResponseListDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .storeId(dto.getStoreId())
                .storeName(dto.getStoreName())
                .canPickup(dto.isCanPickup())
                .canDelivery(dto.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(dto.getCategory().split(","))));
    }
}
