package com.team9ookie.dangdo.entity;

import com.team9ookie.dangdo.dto.store.Platform;
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
public class StoreLink extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

}
