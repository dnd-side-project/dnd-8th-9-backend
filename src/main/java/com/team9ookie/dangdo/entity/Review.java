package com.team9ookie.dangdo.entity;

import com.team9ookie.dangdo.dto.review.GoodPoint;
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
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private int dangdo;

    @Column
    private GoodPoint goodPoint;

    @Column
    private boolean reorder;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
