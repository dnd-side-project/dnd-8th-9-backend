package com.team9ookie.dangdo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String location;

    @Column
    private String businessHours;

    @Column(columnDefinition = "TEXT")
    private String orderForm;

    @Column(columnDefinition = "LONGTEXT")
    private String notice;

    @Column
    private boolean canPickup;

    @Column
    private boolean canDelivery;

    @Column
    private String category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Menu> menuList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreLink> storeLinkList;

}
