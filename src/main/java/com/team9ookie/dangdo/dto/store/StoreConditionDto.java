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

    private String search;

    private List<String> categories;

    @Builder.Default
    private int minPrice = 0;

    @Builder.Default
    private int maxPrice = 9999999;

    private List<String> platforms;

    private String receive;

    @Builder.Default
    private String sort = "latest";

}
