package com.team9ookie.dangdo.dto.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team9ookie.dangdo.entity.Store;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreRequestDto {

    private String name;

    private String location;

    private String phone;

    private int minOrderDue;

    private Map<String, String> businessHours = new LinkedHashMap<>();

    private List<StoreLinkDto> links = new ArrayList<>();

    private Map<String, String> orderForm = new LinkedHashMap<>();

    private String notice;

    private String info;

    private boolean canPickup = false;

    private boolean canDelivery = false;

    @Builder.Default
    private List<String> category = new ArrayList<>();

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    public Store toEntity() {
        return Store.builder()
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

    public Store toEntity(long id) {
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

}
