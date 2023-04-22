package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;
    private Map<String,String> basicInfo;
    private String detailInfo;
    private List<String> category;
    private long storeId;

    public Menu toEntity(Store store) {
        return Menu.builder().name(name).price(price).basicInfo(basicInfo).detailInfo(detailInfo).category(String.join(",",category)).store(store).build();
    }
}
