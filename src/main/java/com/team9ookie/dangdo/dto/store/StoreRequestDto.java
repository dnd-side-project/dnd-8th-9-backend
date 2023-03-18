package com.team9ookie.dangdo.dto.store;

import com.team9ookie.dangdo.entity.Store;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
public class StoreRequestDto {

    private String name;

    private String location;

    private List<StoreLinkDto> links;

    private String businessHours;

    private Map<String, Object> orderForm;

    private Map<String, Object> notice;

    private Map<String, Object> info;

    private boolean canPickup;

    private boolean canDelivery;

    @Builder.Default
    private List<String> category = new ArrayList<>();

    public Store toEntity() {
        return Store.builder()
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

    public Store toEntity(long id) {
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

}
