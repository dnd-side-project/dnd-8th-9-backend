package com.team9ookie.dangdo.repository;

import com.team9ookie.dangdo.dto.store.Platform;
import com.team9ookie.dangdo.dto.store.StoreConditionDto;
import com.team9ookie.dangdo.entity.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomStoreRepository {

    private final EntityManager entityManager;

    public List<Store> findAllByCondition(StoreConditionDto condition) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Store> query = builder.createQuery(Store.class);
        Root<Store> root = query.from(Store.class);

        // 카테고리
        List<String> categoryList = condition.getCategories();
        if (categoryList != null) {
            List<Predicate> categoryPredicate = categoryList.stream().map(s -> builder.like(
                    root.get("category"), "%" + s + "%"
            )).toList();
            query = query.where(builder.or(categoryPredicate.toArray(new Predicate[0])));
        }

        // TODO: 메뉴 가격 최소/최대가격 필터링 추가


        // 플랫폼
        List<String> platformNameList = condition.getPlatforms();
        if (platformNameList != null) {
            List<Platform> platformList = platformNameList.stream().map(Platform::findByName).toList();
            List<Predicate> platformPredicate = platformList
                    .stream()
                    .map(s -> root.join("storeLinkList").get("platform").in(s))
                    .toList();
            query = query.where(builder.or(platformPredicate.toArray(new Predicate[0])));
        }

        // 수령 방법
        String receive = condition.getReceive();
        if (receive != null) {
            if (receive.equals("canPickup")) {
                query = query.where(builder.equal(
                        root.get("canPickup"), true
                ));
            } else if (receive.equals("canDelivery")) {
                query = query.where(builder.equal(
                        root.get("canDelivery"), true
                ));
            }
        }

        // TODO: 정렬에 당도 높은 순 추가
        query.orderBy(builder.desc(root.get("id")));

        return entityManager.createQuery(query).getResultList();
    }

}
