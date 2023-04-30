package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.entity.StoreBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreBookmarkRepository extends JpaRepository<StoreBookmark, Long> {

    StoreBookmark findByUserIdAndStoreId(long userId, long storeId);

}
