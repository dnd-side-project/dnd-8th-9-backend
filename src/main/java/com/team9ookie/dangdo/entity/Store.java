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

    @Column(nullable = false)
    private String name;

    @Column
    private String location;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, String> orderForm;

    @Column
    private String notice;

    @Column
    private String info;

    @Column
    private boolean canPickup;

    @Column
    private boolean canDelivery;

    @Column
    private String category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Menu> menuList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreLink> storeLinkList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<StoreBookmark> storeBookmarkList;

}
