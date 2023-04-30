package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.MenuBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuBookmarkRepository extends JpaRepository<MenuBookmark, Long> {

    MenuBookmark findByUserIdAndMenuId(long userId, long menuId);

}
