package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStore_Id(@Param(value = "storeId") Long storeId);

    List<Menu> findByNameContainingIgnoreCase(String name);
}
