package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.dto.store.*;
import com.team9ookie.dangdo.entity.FileEntity;
import com.team9ookie.dangdo.entity.Store;
import com.team9ookie.dangdo.entity.StoreLink;
import com.team9ookie.dangdo.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final FileService fileService;

    private final StoreRepository storeRepository;

    private final MenuRepository menuRepository;

    private final ReviewRepository reviewRepository;

    private final StoreLinkRepository storeLinkRepository;

    private final CustomStoreRepository customStoreRepository;

    private final FileRepository fileRepository;

    // TODO: 테스트용 예제 데이터 생성 코드. 더미 데이터 생성 기능 구현 후 제거
    @PostConstruct
    public void initData() {
        if (storeRepository.count() > 0) return;

        List<Store> exampleStoreList = IntStream.range(1, 4).boxed().map(index -> Store.builder()
                .name("당도케이크 [%s]".formatted(index))
                .location("서울특별시 당도구 당도로 5길 9")
                .businessHours("11시-20시")
                .orderForm("성함/연락처: \n픽업 날짜, 시간: 월 일 요일 시 분 \n크기: 호 \n맛: \n디자인: 케이크 사진을 보내주세요! \n하트 or 원형 : \n케이크위 레터링: \n케이크밑판문구: \n요청사항:")
                .notice("- 사용되는 꼿은 식용꽃으로 랜덤으로 사용됩니다. 꽃잎 컬러 및 모양은 지정 불가합니다. \n*요청사항에 기재하셔도 변경 내용은 적용되지 않습니다. \n- 사용된 조화는 수급에 따라 비슷한 조화로 대체될수 있습니다. \n- 먼슬리 디자인은 디자인 및 케이크 칼라, 레터링 칼라, 레터링 위치 등 변경 불가합니다. \n*요청사항에 기재하셔도 변경 내용은 적용되지 않습니다. \n- 6,7,8 번 디자인은 사이즈업 불가합니다. \n- 1,3,6,7,8,번은 영문 레터링만 가능합니다.(한글불가) *한글로 기재시 기본 문구로 제작 됩니다. \n- 모든 식용 꽃은 세척 및 소독후 사용됩니다. 꽃잎은 식용이라 드실수 있지만 질감의 이질감을 줄수 있어서 떼고 드시는걸 추천드립니다. \n- 진주는 슈가로 만들어진 스프링클입니다. 크기가 큰 스프링클은 씹어 드실경우 치아 손상이 있을 수 있으니 제거하고 드세요.")
                .canPickup(true)
                .canDelivery(false)
                .category("레터링 케이크,캐릭터 케이크")
                .build()).toList();
        storeRepository.saveAll(exampleStoreList);

        List<StoreLink> exampleStoreLinkList = Arrays.asList(
                StoreLink.builder().platform(Platform.INSTAGRAM).url("https://example-store.com").store(exampleStoreList.get(0)).build(),
                StoreLink.builder().platform(Platform.KAKAO).url("https://example-store.com").store(exampleStoreList.get(1)).build()
        );
        storeLinkRepository.saveAll(exampleStoreLinkList);

        List<FileEntity> exampleFileEntityList = Arrays.asList(
                FileEntity.builder().type(FileType.STORE_IMAGE).url("https://example-file.com").targetId(1L).build(),
                FileEntity.builder().type(FileType.STORE_IMAGE).url("https://example-file.com").targetId(3L).build()
        );
        fileRepository.saveAll(exampleFileEntityList);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAll(StoreConditionDto conditionDto) {
        List<Store> storeList = null;
        if (conditionDto == null) {
            storeList = storeRepository.findAll();
        } else{
            storeList = customStoreRepository.findAllByCondition(conditionDto);
        }

        return storeList.stream().map(store -> {
            // 평균 당도 (소수점 한 자리까지 나타냄)
            int rating = getAverageRating(store.getId());

            // 최소, 최대 메뉴 금액
            PriceRange priceRange = getPriceRange(store.getId());

            // 업체와 연결된 링크, 파일
            List<StoreLink> storeLinkList = storeLinkRepository.findAllByStoreId(store.getId());
            List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.STORE_IMAGE, store.getId());
            return StoreResponseDto.create(store)
                    .rating(rating)
                    .priceRange(priceRange)
                    .links(storeLinkList.stream().map(StoreLinkDto::of).toList())
                    .storeImages(fileEntityList.stream().map(FileDto::of).toList())
                    .build();
        }).toList();
    }

    @Transactional(readOnly = true)
    public StoreResponseDto get(long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. id: " + id));

        // 평균 당도 (소수점 한 자리까지 나타냄)
        int rating = getAverageRating(store.getId());

        // 최소, 최대 메뉴 금액
        PriceRange priceRange = getPriceRange(store.getId());

        // 업체와 연결된 링크, 파일
        List<StoreLink> storeLinkList = storeLinkRepository.findAllByStoreId(id);
        List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.STORE_IMAGE, id);
        return StoreResponseDto.create(store)
                .rating(rating)
                .priceRange(priceRange)
                .links(storeLinkList.stream().map(StoreLinkDto::of).toList())
                .storeImages(fileEntityList.stream().map(FileDto::of).toList())
                .build();
    }

    @Transactional
    public long create(StoreRequestDto dto, List<MultipartFile> fileList) throws Exception {
        Store store = storeRepository.save(dto.toEntity());

        if (fileList != null && !fileList.isEmpty()) {
            List<FileEntity> fileEntityList = fileService.createFileList(fileList, FileType.STORE_IMAGE, store.getId());
            fileRepository.saveAll(fileEntityList);
        }

        List<StoreLinkDto> storeLinkDtoList = dto.getLinks();
        if (storeLinkDtoList != null && !storeLinkDtoList.isEmpty()) {
            List<StoreLink> storeLinkList = dto.getLinks().stream().map(storeLinkDto -> StoreLink.builder()
                    .platform(Platform.findByName(storeLinkDto.getPlatform()))
                    .url(storeLinkDto.getUrl())
                    .store(store)
                    .build()).toList();
            storeLinkRepository.saveAll(storeLinkList);
        }

        return store.getId();
    }

    @Transactional
    public StoreResponseDto update(long id, StoreRequestDto dto, List<MultipartFile> fileList) throws Exception {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. id: " + id));

        Store updatedStore = storeRepository.save(dto.toEntity(id));

        fileRepository.deleteAllByTypeAndTargetId(FileType.STORE_IMAGE, id);
        List<FileEntity> fileEntityList = new ArrayList<>();
        if (fileList != null && !fileList.isEmpty()) {
            fileEntityList = fileService.createFileList(fileList, FileType.STORE_IMAGE, store.getId());
            fileRepository.saveAll(fileEntityList);
        }

        storeLinkRepository.deleteAllByStoreId(id);
        List<StoreLink> storeLinkList = new ArrayList<>();
        List<StoreLinkDto> storeLinkDtoList = dto.getLinks();
        if (storeLinkDtoList != null && !storeLinkDtoList.isEmpty()) {
            storeLinkList = dto.getLinks().stream().map(storeLinkDto -> StoreLink.builder()
                    .platform(Platform.findByName(storeLinkDto.getPlatform()))
                    .url(storeLinkDto.getUrl())
                    .store(store)
                    .build()).toList();
            storeLinkRepository.saveAll(storeLinkList);
        }

        return StoreResponseDto.create(updatedStore)
                .rating(getAverageRating(store.getId()))
                .priceRange(getPriceRange(store.getId()))
                .links(storeLinkList.stream().map(StoreLinkDto::of).toList())
                .storeImages(fileEntityList.stream().map(FileDto::of).toList())
                .build();
    }

    @Transactional
    public long delete(long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. id: " + id));
        storeRepository.deleteById(store.getId());
        return store.getId();
    }

    private int getAverageRating(Long storeId) {
        return (int) Math.round(reviewRepository.getAverageRate(storeId) * 20);
    }

    private PriceRange getPriceRange(Long storeId) {
        Map<String, Integer> priceRange = menuRepository.getPriceRange(storeId);
        int minPrice = priceRange.get("minPrice");
        int maxPrice = priceRange.get("maxPrice");
        return PriceRange.builder().min(minPrice).max(maxPrice).build();
    }

}
