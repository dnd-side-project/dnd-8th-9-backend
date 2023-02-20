package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.StoreLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreLinkRepository extends JpaRepository<StoreLink, Long> {

    List<StoreLink> findAllByStoreId(long id);

    void deleteAllByStoreId(long id);

}
