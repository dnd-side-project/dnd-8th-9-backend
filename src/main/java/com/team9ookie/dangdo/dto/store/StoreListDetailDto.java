package com.team9ookie.dangdo.dto.store;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreListDetailDto {

    private Long id;

    private String name;

    private String location;

    private double rating;

    private int reviewCount;

    private int minPrice;

    private int maxPrice;

    private boolean canPickup;

    private boolean canDelivery;

    private String category;

}
