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
import com.team9ookie.dangdo.dto.menu.MenuConditionDto;
import com.team9ookie.dangdo.dto.menu.MenuListDetailDto;
import com.team9ookie.dangdo.dto.store.Platform;
import com.team9ookie.dangdo.entity.QMenuBookmark;
import com.team9ookie.dangdo.entity.QReview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team9ookie.dangdo.entity.QMenu.menu;
import static com.team9ookie.dangdo.entity.QStore.store;
import static com.team9ookie.dangdo.entity.QStoreLink.storeLink;

@Repository
@RequiredArgsConstructor
public class CustomMenuRepository {
    private final JPQLQueryFactory queryFactory;

    public List<MenuListDetailDto> getMenuListByCondition(MenuConditionDto condition, Pageable pageable) {

        QReview review = QReview.review;
        NumberExpression<Double> rating = review.dangdo.avg().multiply(20).ceil();
        NumberExpression<Integer> reviewCount = menu.reviewList.size().castToNum(Integer.class);
        NumberExpression<Integer> bookmarkCount = menu.menuBookmarkList.size().castToNum(Integer.class);

        JPQLQuery<MenuListDetailDto> query = queryFactory
                .select(Projections.bean(
                        MenuListDetailDto.class,
                        menu.id,
                        menu.name,
                        menu.category,
                        menu.price,
                        rating.as("rating"),
                        reviewCount.as("reviewCount"),
                        bookmarkCount.as("bookmarkCount"),
                        store.id.as("storeId"),
                        store.name.as("storeName"),
                        store.canPickup,
                        store.canDelivery
                        )
                )
                .from(menu)
                .leftJoin(menu.store, store)
                .leftJoin(menu.reviewList, review)
                .leftJoin(store.storeLinkList, storeLink)
                .groupBy(menu.id);

        BooleanExpression searchFiltering = searchFiltering(condition.getSearch());
        Predicate categoryFiltering = categoryFiltering(condition.getCategories());
        BooleanExpression minPriceExpression = menu.price.goe(condition.getMinPrice());
        BooleanExpression maxPriceExpression = menu.price.loe(condition.getMaxPrice());
        BooleanExpression platformFiltering = platformFiltering(condition.getPlatforms());
        BooleanExpression receiveMethodFiltering = receiveMethodFiltering(condition.getReceive());
        BooleanExpression userFiltering = userFiltering(condition.getUserId());

        query = query.where(
                searchFiltering,
                categoryFiltering,
                platformFiltering,
                receiveMethodFiltering,
                minPriceExpression,
                maxPriceExpression,
                userFiltering
        );

        String sort = condition.getSort();
        query = switch (sort) {
            case "latest" -> query.orderBy(new OrderSpecifier<>(Order.DESC, menu.id));
            case "dangdo", "recommend" -> query.orderBy(new OrderSpecifier<>(Order.DESC, rating));
            default -> query;
        };

        List<MenuListDetailDto> result = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return result;
    }

    private BooleanExpression searchFiltering(String search) {
        if (search == null) return null;
        return menu.name.like("%" + search + "%");
    }

    private Predicate categoryFiltering(List<String> categoryList) {
        if (categoryList == null) return null;
        BooleanBuilder builder = new BooleanBuilder();
        categoryList.forEach(s -> {
            builder.or(menu.category.like("%" + s + "%"));
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

    private BooleanExpression userFiltering(long userId) {
        if (userId == 0) return null;
        QMenuBookmark menuBookmark = QMenuBookmark.menuBookmark;
        List<Long> menuList = queryFactory
                .select(menuBookmark.menu.id)
                .from(menuBookmark)
                .where(menuBookmark.user.id.eq(userId))
                .fetch();
        return menu.id.in(menuList);
    }

}
