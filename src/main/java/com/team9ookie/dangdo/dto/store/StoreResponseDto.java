package com.team9ookie.dangdo.dto.store;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.entity.Store;
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
public class StoreResponseDto {

    private long id;

    private String name;

    private String location;

    private double rating;

    private PriceRange priceRange;

    private List<StoreLinkDto> links;

    private String businessHours;

    private String orderForm;

    private String notice;

    private boolean canPickup;

    private boolean canDelivery;

    private List<String> category;

    private List<FileDto> storeImages;

    public Store toEntity() {
        return Store.builder()
                .id(id)
                .name(name)
                .location(location)
                .businessHours(businessHours)
                .orderForm(orderForm)
                .notice(notice)
                .canPickup(canPickup)
                .canDelivery(canDelivery)
                .category(String.join(",", category))
                .build();
    }

    public static class StoreResponseDtoBuilder {}

    public static StoreResponseDtoBuilder create(Store store) {
        return StoreResponseDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .businessHours(store.getBusinessHours())
                .orderForm(store.getOrderForm())
                .notice(store.getNotice())
                .canPickup(store.isCanPickup())
                .canDelivery(store.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(store.getCategory().split(","))));
    }

    public static StoreResponseDtoBuilder create(StoreDetailDto dto) {
        return StoreResponseDto.builder()
                .id(dto.getId())
                .name(dto.getName())
                .location(dto.getLocation())
                .rating(dto.getRating())
                .priceRange(PriceRange.builder().min(dto.getMinPrice()).max(dto.getMaxPrice()).build())
                .businessHours(dto.getBusinessHours())
                .orderForm(dto.getOrderForm())
                .notice(dto.getNotice())
                .canPickup(dto.isCanPickup())
                .canDelivery(dto.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(dto.getCategory().split(","))));
    }

}
