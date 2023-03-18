package com.team9ookie.dangdo.dto.store;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailDto {

    private Long id;

    private String name;

    private String location;

    private double rating;

    private int reviewCount;

    private int minPrice;

    private int maxPrice;

    private String businessHours;

    private Map<String, Object> orderForm;

    private Map<String, Object> notice;

    private Map<String, Object> info;

    private boolean canPickup;

    private boolean canDelivery;

    private String category;

}
