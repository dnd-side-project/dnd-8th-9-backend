package com.team9ookie.dangdo.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(indexes = @Index(name="name_index", columnList = "name"))
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

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> orderForm;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> notice;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> info;

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
