package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;
    private String caution;
    private String description;
    private List<String> category;
    private String summary;

    //Todo
    public Menu toEntity(Store store) {
        return Menu.builder().name(name).price(price).caution(caution).description(description).category(String.join(",",category)).summary(summary).store(store).build();
    }
}
