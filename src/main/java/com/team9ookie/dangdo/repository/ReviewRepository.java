package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMenuId(long menuId);

}
