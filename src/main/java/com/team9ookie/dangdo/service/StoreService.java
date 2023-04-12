package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.dto.store.*;
import com.team9ookie.dangdo.entity.FileEntity;
import com.team9ookie.dangdo.entity.Store;
import com.team9ookie.dangdo.entity.StoreLink;
import com.team9ookie.dangdo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Transactional(readOnly = true)
    public List<StoreListResponseDto> getAll(StoreConditionDto conditionDto) {
        Pageable pageable = PageRequest.of(conditionDto.getPage(), 10);
        List<StoreListDetailDto> storeDetailDtoList = customStoreRepository.getStoreListByCondition(conditionDto, pageable);

        return storeDetailDtoList.stream().map(dto -> {
            // 업체와 연결된 링크, 파일
            List<StoreLink> storeLinkList = storeLinkRepository.findAllByStoreId(dto.getId());
            List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.STORE_IMAGE, dto.getId());
            return StoreListResponseDto.create(dto)
                    .links(storeLinkList.stream().map(StoreLinkDto::of).toList())
                    .storeImages(fileEntityList.stream().map(FileDto::of).toList())
                    .build();
        }).toList();
    }

    @Transactional(readOnly = true)
    public StoreResponseDto get(long id) {
        StoreDetailDto store = customStoreRepository.getStoreById(id);

        // 업체와 연결된 링크, 파일
        List<StoreLink> storeLinkList = storeLinkRepository.findAllByStoreId(id);
        List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.STORE_IMAGE, id);
        return StoreResponseDto.create(store)
                .links(storeLinkList.stream().map(StoreLinkDto::of).toList())
                .storeImages(fileEntityList.stream().map(FileDto::of).toList())
                .build();
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> searchStoresByName(String name) {
        List<Store> storeList = storeRepository.findByNameContainingIgnoreCase(name);

        return storeList.stream().map(store -> {
            List<FileEntity> storeImages = fileRepository.findAllByTypeAndTargetId(FileType.MENU_IMAGE, store.getId());
            return StoreResponseDto.create(store).storeImages(storeImages.stream().map(FileDto::of).toList()).build();
        }).toList();
    }

    @Transactional
    public long create(StoreRequestDto dto) throws Exception {
        Store store = storeRepository.save(dto.toEntity());

        List<MultipartFile> fileList = dto.getFiles();
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
    public StoreResponseDto update(long id, StoreRequestDto dto) throws Exception {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체를 찾을 수 없습니다. id: " + id));

        Store updatedStore = storeRepository.save(dto.toEntity(id));

        fileRepository.deleteAllByTypeAndTargetId(FileType.STORE_IMAGE, id);
        List<MultipartFile> fileList = dto.getFiles();
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
