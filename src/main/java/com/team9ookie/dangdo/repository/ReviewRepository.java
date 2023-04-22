package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.Review;
import com.team9ookie.dangdo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMenuId(long menuId);

    List<Review> findByStoreId(long storeId);

    @Query("select coalesce(AVG(r.dangdo), 0) as avg from Review r where r.menu.store.id = :storeId")
    double getAverageRate(Long storeId);

}
