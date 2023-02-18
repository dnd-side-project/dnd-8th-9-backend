package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;
    private String caution;
    private String description;
    private String category;

    @Builder
    public MenuRequestDto(String name, int price, String caution, String description, String category) {
        this.name = name;
        this.price = price;
        this.caution = caution;
        this.description = description;
        this.category = category;
    }

    public Menu toEntity(Store store) {
        return Menu.builder().name(name).price(price).caution(caution).description(description).category(category).store(store).build();
    }
}
