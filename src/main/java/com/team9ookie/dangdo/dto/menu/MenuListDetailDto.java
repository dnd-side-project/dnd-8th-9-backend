package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.dto.store.StoreListDetailDto;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuListDetailDto {
    private Long id;

    private String name;

    private String category;

    private int price;

    private double rating;

    private int reviewCount;

    private Long storeId;

    private String storeName;

    private boolean canPickup;

    private boolean canDelivery;
}
