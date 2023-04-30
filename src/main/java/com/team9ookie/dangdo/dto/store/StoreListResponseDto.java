package com.team9ookie.dangdo.dto.store;

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
public class StoreListResponseDto {

    private long id;

    private String name;

    private String location;

    private double rating;

    private int reviewCount;

    private int bookmarkCount;

    private PriceRange priceRange;

    private List<StoreLinkDto> links;

    private boolean canPickup;

    private boolean canDelivery;

    private List<String> category;

    private List<FileDto> storeImages;

    public static class StoreResponseDtoBuilder {}

    public static StoreListResponseDtoBuilder create(StoreListDetailDto dto) {
        return StoreListResponseDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(dto.getLocation())
                .rating(dto.getRating())
                .reviewCount(dto.getReviewCount())
                .bookmarkCount(dto.getBookmarkCount())
                .priceRange(PriceRange.builder().min(dto.getMinPrice()).max(dto.getMaxPrice()).build())
                .canPickup(dto.isCanPickup())
                .canDelivery(dto.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(dto.getCategory().split(","))));
    }

}
