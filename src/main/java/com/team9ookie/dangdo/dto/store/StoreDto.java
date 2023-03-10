package com.team9ookie.dangdo.dto.store;

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
public class StoreDto {

    private long id;

    private String name;

    private String location;

    private String businessHours;

    private String orderForm;

    private String notice;

    private boolean canPickup;

    private boolean canDelivery;

    private List<String> category;

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

    public static StoreDto of(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .businessHours(store.getBusinessHours())
                .orderForm(store.getOrderForm())
                .notice(store.getNotice())
                .canPickup(store.isCanPickup())
                .canDelivery(store.isCanDelivery())
                .category(new ArrayList<>(Arrays.asList(store.getCategory().split(","))))
                .build();
    }

}
