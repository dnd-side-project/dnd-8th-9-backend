package com.team9ookie.dangdo.entity;

import com.team9ookie.dangdo.dto.menu.MenuRequestDto;
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
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String,String> basicInfo;

    @Column(columnDefinition = "TEXT")
    private String detailInfo;
    @Column
    private String category;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuBookmark> menuBookmarkList;

    public void update(MenuRequestDto requestDto, Store store) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.basicInfo = requestDto.getBasicInfo();
        this.detailInfo = requestDto.getDetailInfo();
        this.category = String.join(",", requestDto.getCategory());
        this.store = store;
    }

}
