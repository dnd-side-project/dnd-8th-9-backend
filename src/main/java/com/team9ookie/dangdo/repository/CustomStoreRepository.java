package com.team9ookie.dangdo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.team9ookie.dangdo.dto.store.Platform;
import com.team9ookie.dangdo.dto.store.StoreConditionDto;
import com.team9ookie.dangdo.dto.store.StoreDetailDto;
import com.team9ookie.dangdo.dto.store.StoreListDetailDto;
import com.team9ookie.dangdo.entity.QMenu;
import com.team9ookie.dangdo.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team9ookie.dangdo.entity.QStore.store;
import static com.team9ookie.dangdo.entity.QStoreLink.storeLink;

@Repository
@RequiredArgsConstructor
public class CustomStoreRepository {

    private final JPQLQueryFactory queryFactory;

    public List<StoreListDetailDto> getStoreListByCondition(StoreConditionDto condition, Pageable pageable) {

        QMenu menu = QMenu.menu;
        QReview review = QReview.review;

        NumberExpression<Double> rating = review.dangdo.avg().multiply(20).ceil();
        NumberExpression<Integer> reviewCount = review.count().castToNum(Integer.class);
        NumberExpression<Integer> minPrice = menu.price.min();
        NumberExpression<Integer> maxPrice = menu.price.max();

        JPQLQuery<StoreListDetailDto> query = queryFactory
                .select(Projections.bean(
                        StoreListDetailDto.class,
                        store.id,
                        store.name,
                        store.location,
                        reviewCount.as("reviewCount"),
                        rating.as("rating"),
                        minPrice.as("minPrice"),
                        maxPrice.as("maxPrice"),
                        store.canPickup,
                        store.canDelivery,
                        store.category
                        )
                )
                .from(store)
                .leftJoin(store.menuList, menu)
                .leftJoin(menu.reviewList, review)
                .leftJoin(store.storeLinkList, storeLink)
                .groupBy(store.id);

        BooleanExpression searchFiltering = searchFiltering(condition.getSearch());
        Predicate categoryFiltering = categoryFiltering(condition.getCategories());
        BooleanExpression minPriceExpression = minPrice.goe(condition.getMinPrice());
        BooleanExpression maxPriceExpression = maxPrice.loe(condition.getMaxPrice());
        BooleanExpression platformFiltering = platformFiltering(condition.getPlatforms());
        BooleanExpression receiveMethodFiltering = receiveMethodFiltering(condition.getReceive());

        query = query.where(
                searchFiltering,
                categoryFiltering,
                platformFiltering,
                receiveMethodFiltering
        );

        query = query.having(
                minPriceExpression,
                maxPriceExpression
        );

        String sort = condition.getSort();
        query = switch (sort) {
            case "latest" -> query.orderBy(new OrderSpecifier<>(Order.DESC, store.id));
            case "dangdo", "recommend" -> query.orderBy(new OrderSpecifier<>(Order.DESC, rating));
            default -> query;
        };

        List<StoreListDetailDto> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return result;
    }

    public StoreDetailDto getStoreById(long id) {

        QMenu menu = QMenu.menu;
        QReview review = QReview.review;

        NumberExpression<Double> rating = review.dangdo.avg().multiply(20).ceil();
        NumberExpression<Integer> reviewCount = review.count().castToNum(Integer.class);
        NumberExpression<Integer> minPrice = menu.price.min();
        NumberExpression<Integer> maxPrice = menu.price.max();

        StoreDetailDto result = queryFactory
                .select(Projections.bean(
                        StoreDetailDto.class,
                        store.id,
                        store.name,
                        store.location,
                        reviewCount.as("reviewCount"),
                        rating.as("rating"),
                        minPrice.as("minPrice"),
                        maxPrice.as("maxPrice"),
                        store.businessHours,
                        store.orderForm,
                        store.notice,
                        store.info,
                        store.canPickup,
                        store.canDelivery,
                        store.category
                        )
                )
                .from(store)
                .leftJoin(store.menuList, menu)
                .leftJoin(menu.reviewList, review)
                .groupBy(store.id)
                .where(store.id.eq(id))
                .fetchOne();

        return result;
    }

    private BooleanExpression searchFiltering(String search) {
        if (search == null) return null;
        return store.name.like("%" + search + "%");
    }

    private Predicate categoryFiltering(List<String> categoryList) {
        if (categoryList == null) return null;
        BooleanBuilder builder = new BooleanBuilder();
        categoryList.forEach(s -> {
            builder.or(store.category.like("%" + s + "%"));
        });
        return builder.getValue();
    }

    private BooleanExpression platformFiltering(List<String> platformNameList) {
        if (platformNameList == null) return null;
        List<Platform> platformList = platformNameList.stream().map(Platform::findByName).toList();
        return storeLink.platform.in(platformList);
    }

    private BooleanExpression receiveMethodFiltering(String receive) {
        if (receive == null) return null;
        return switch (receive) {
            case "canPickup" -> store.canPickup.eq(true);
            case "canDelivery" -> store.canDelivery.eq(true);
            default -> null;
        };
    }
}
