package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByNameContainingIgnoreCase(String name);
}
