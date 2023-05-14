package com.team9ookie.dangdo.dto.store;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.entity.Store;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private String phone;

    private int minOrderDue;

    private Map<String, String> businessHours;

    private double rating;

    private int reviewCount;

    private Map<String, Integer> reviewStats;

    private PriceRange priceRange;

    private Map<String, String> orderForm;

    private String notice;

    private String info;

    private boolean canPickup;

    private boolean canDelivery;

    private List<String> category;

    private List<StoreLinkDto> links;

    private List<FileDto> storeImages;

    public Store toEntity() {
        return Store.builder()
                .id(id)
                .name(name)
                .location(location)
                .phone(phone)
                .minOrderDue(minOrderDue)
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
                .phone(store.getPhone())
                .minOrderDue(store.getMinOrderDue())
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
                .phone(dto.getPhone())
                .minOrderDue(dto.getMinOrderDue())
                .businessHours(dto.getBusinessHours())
                .rating(dto.getRating())
                .reviewCount(dto.getReviewCount())
                .reviewStats(dto.getReviewStats())
                .priceRange(PriceRange.builder().min(dto.getMinPrice()).max(dto.getMaxPrice()).build())
                .orderForm(dto.getOrderForm())
                .notice(dto.getNotice())
                .info(dto.getInfo())
                .canPickup(dto.isCanPickup())
                .canDelivery(dto.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(dto.getCategory().split(","))));
    }

}
