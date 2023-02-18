package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuResponseDto {

    private long menuId;
    private String name;
    private int price;
    private String caution;
    private String description;
    private String category;

    public MenuResponseDto(Menu entity) {
        this.menuId = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.caution = entity.getCaution();
        this.description = entity.getDescription();
        this.category = entity.getCategory();
    }
}
