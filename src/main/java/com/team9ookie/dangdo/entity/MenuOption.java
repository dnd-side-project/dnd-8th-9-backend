package com.team9ookie.dangdo.entity;

import com.team9ookie.dangdo.dto.MenuOptionType;
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
public class MenuOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private MenuOptionType type;

    @Column
    private String value;

    @Column(columnDefinition = "LONGTEXT")
    private String note;

}
