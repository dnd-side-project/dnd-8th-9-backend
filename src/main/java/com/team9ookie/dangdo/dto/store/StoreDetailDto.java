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

    private Map<String, Integer> reviewStats;

    private int minPrice;

    private int maxPrice;

    private Map<String, String> orderForm;

    private String notice;

    private String info;

    private boolean canPickup;

    private boolean canDelivery;

    private String category;

}
