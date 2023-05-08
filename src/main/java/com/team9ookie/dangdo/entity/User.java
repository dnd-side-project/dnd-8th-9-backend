package com.team9ookie.dangdo.entity;


import com.team9ookie.dangdo.auth.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;

    @Column
    private String nickname;

    @Column
    private String profileImg;
    @Column
    private boolean active;

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StoreBookmark> storeBookmarkList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MenuBookmark> menuBookmarkList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList;

}
