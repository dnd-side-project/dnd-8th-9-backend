package com.team9ookie.dangdo.dto.store;

import lombok.*;

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

    private int minPrice;

    private int maxPrice;

    private String businessHours;

    private String orderForm;

    private String notice;

    private boolean canPickup;

    private boolean canDelivery;

    private String category;

}
