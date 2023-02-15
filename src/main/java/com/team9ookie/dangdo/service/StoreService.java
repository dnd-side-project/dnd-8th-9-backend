package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.StoreDto;
import com.team9ookie.dangdo.entity.Store;
import com.team9ookie.dangdo.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 테스트용 예제 데이터 생성 코드. 더미 데이터 생성 기능 구현 후 제거
    @PostConstruct
    public void initData() {
        if (storeRepository.count() > 0) return;

        Store store = Store.builder()
                .name("당도케이크")
                .location("서울특별시 당도구 당도로 5길 9")
                .businessHours("11시-20시")
                .orderForm("성함/연락처: \n픽업 날짜, 시간: 월 일 요일 시 분 \n크기: 호 \n맛: \n디자인: 케이크 사진을 보내주세요! \n하트 or 원형 : \n케이크위 레터링: \n케이크밑판문구: \n요청사항:")
                .notice("- 사용되는 꼿은 식용꽃으로 랜덤으로 사용됩니다. 꽃잎 컬러 및 모양은 지정 불가합니다. \n*요청사항에 기재하셔도 변경 내용은 적용되지 않습니다. \n- 사용된 조화는 수급에 따라 비슷한 조화로 대체될수 있습니다. \n- 먼슬리 디자인은 디자인 및 케이크 칼라, 레터링 칼라, 레터링 위치 등 변경 불가합니다. \n*요청사항에 기재하셔도 변경 내용은 적용되지 않습니다. \n- 6,7,8 번 디자인은 사이즈업 불가합니다. \n- 1,3,6,7,8,번은 영문 레터링만 가능합니다.(한글불가) *한글로 기재시 기본 문구로 제작 됩니다. \n- 모든 식용 꽃은 세척 및 소독후 사용됩니다. 꽃잎은 식용이라 드실수 있지만 질감의 이질감을 줄수 있어서 떼고 드시는걸 추천드립니다. \n- 진주는 슈가로 만들어진 스프링클입니다. 크기가 큰 스프링클은 씹어 드실경우 치아 손상이 있을 수 있으니 제거하고 드세요.")
                .canPickup(true)
                .canDelivery(false)
                .category("레터링 케이크,캐릭터 케이크")
                .build();
        storeRepository.save(store);
    }

    public StoreDto get(long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. id: " + id));
        return StoreDto.of(store);
    }

}
