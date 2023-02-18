package com.team9ookie.dangdo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private String caution;

    @Column
    private String description;

    @Column
    private String category;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public void update(String name, int price, String caution, String description, String category, Store store) {
        this.name = name;
        this.price = price;
        this.caution = caution;
        this.description = description;
        this.category = category;
        this.store = store;
    }
}
