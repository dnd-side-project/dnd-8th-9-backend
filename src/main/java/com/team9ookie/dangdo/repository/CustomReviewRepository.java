package com.team9ookie.dangdo.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQueryFactory;
import com.team9ookie.dangdo.dto.review.GoodPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team9ookie.dangdo.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class CustomReviewRepository {

    private final JPQLQueryFactory queryFactory;

    public Map<String, Integer> findReviewStats(long storeId) {
        List<Tuple> tupleList = queryFactory
                .select(review.goodPoint, review.count().castToNum(Integer.class))
                .from(review)
                .where(review.store.id.eq(storeId))
                .groupBy(review.goodPoint)
                .fetch();
        Map<String, Integer> reviewStats = new HashMap<>();
        for (Tuple tuple : tupleList) {
            GoodPoint goodPoint = tuple.get(review.goodPoint);
            String message = goodPoint.getMessage();
            Integer count = tuple.get(review.count().castToNum(Integer.class));
            reviewStats.put(message, count);
        }
        return reviewStats;
    }

}
