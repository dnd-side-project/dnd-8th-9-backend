package com.team9ookie.dangdo.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQueryFactory;
import com.team9ookie.dangdo.dto.review.GoodPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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
                .orderBy(review.count().desc())
                .fetch();
        Map<String, Integer> reviewStats = new LinkedHashMap<>();
        Set<GoodPoint> goodPointSet = Arrays.stream(GoodPoint.values()).collect(Collectors.toSet());
        for (Tuple tuple : tupleList) {
            GoodPoint goodPoint = tuple.get(review.goodPoint);
            goodPointSet.remove(goodPoint);
            String message = goodPoint.getMessage();
            Integer count = tuple.get(review.count().castToNum(Integer.class));
            reviewStats.put(message, count);
        }
        for (GoodPoint goodPoint : goodPointSet) {
            reviewStats.put(goodPoint.getMessage(), 0);
        }
        return reviewStats;
    }

}
