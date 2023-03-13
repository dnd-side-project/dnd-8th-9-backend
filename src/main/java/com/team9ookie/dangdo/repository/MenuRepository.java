package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.dto.store.PriceRange;
import com.team9ookie.dangdo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Map;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStore_Id(@Param(value = "storeId") Long storeId);

    @Query("select coalesce(min(m.price), 0) as minPrice, coalesce(max(m.price), 0) as maxPrice from Menu m where m.store.id = :storeId")
    Map<String, Integer> getPriceRange(Long storeId);


    List<Menu> findByNameContainingIgnoreCase(String name);
}
