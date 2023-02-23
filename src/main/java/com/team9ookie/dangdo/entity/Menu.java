package com.team9ookie.dangdo.entity;

import com.team9ookie.dangdo.dto.menu.MenuRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    @Column(columnDefinition = "TEXT")
    private String caution;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String summary;
    @Column
    private String category;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public void update(MenuRequestDto requestDto, Store store) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.caution = requestDto.getCaution();
        this.description = requestDto.getDescription();
        this.category = String.join(",", requestDto.getCategory());
        this.store = store;
    }
}
