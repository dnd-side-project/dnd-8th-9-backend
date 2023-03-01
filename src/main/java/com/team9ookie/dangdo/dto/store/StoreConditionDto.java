package com.team9ookie.dangdo.dto.store;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreConditionDto {

    private List<String> categories;

    private int minPrice = 0;

    private int maxPrice = 9999999;

    private List<String> platforms;

    private String receive;

    private String sort = "latest";

}
