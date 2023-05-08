package com.team9ookie.dangdo.dto.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.store.StoreLinkDto;
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
    @JsonIgnore
    private List<String> category;
    private int price;

    private List<FileDto> menuImage;
    @JsonIgnore
    private int reviewCount;
    @JsonIgnore
    private int bookmarkCount;

    private Long storeId;

    private String storeName;
    @JsonIgnore
    private boolean canPickup;
    @JsonIgnore
    private boolean canDelivery;

    private List<StoreLinkDto> links;

    public static MenuResponseListDtoBuilder create(MenuListDetailDto dto) {
        return MenuResponseListDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .reviewCount(dto.getReviewCount())
                .bookmarkCount(dto.getBookmarkCount())
                .storeId(dto.getStoreId())
                .storeName(dto.getStoreName())
                .canPickup(dto.isCanPickup())
                .canDelivery(dto.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(dto.getCategory().split(","))));
    }
}
