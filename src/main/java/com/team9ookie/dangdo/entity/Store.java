package com.team9ookie.dangdo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Type;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table(indexes = @Index(name="name_index", columnList = "name"))
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String location;

    @Column
    private String phone;

    @Column
    private int minOrderDue;

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, String> businessHours;

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

    @Component
    @RequiredArgsConstructor
    @Slf4j
    static class MapConverter implements Converter<String, Map> {

        private final ObjectMapper objectMapper;

        @Override
        public Map convert(String source) {
            try {
                return objectMapper.readValue(source, Map.class);
            } catch (Exception e) {
                log.error("String -> Map 변환 실패", e);
            }
            return null;
        }
    }

}
