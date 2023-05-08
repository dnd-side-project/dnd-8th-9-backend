package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.dto.menu.*;
import com.team9ookie.dangdo.dto.store.StoreLinkDto;
import com.team9ookie.dangdo.entity.*;
import com.team9ookie.dangdo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final FileRepository fileRepository;
    private final StoreService storeService;
    private final FileService fileService;
    private final CustomMenuRepository customMenuRepository;
    private final MenuBookmarkRepository menuBookmarkRepository;
    private final StoreLinkRepository storeLinkRepository;

    @Transactional
    public Long save(MenuRequestDto requestDto, List<MultipartFile> fileList) throws Exception {
        Store store = storeService.findById(requestDto.getStoreId()).toEntity();
        Long id = menuRepository.save(requestDto.toEntity(store)).getId();

        if (fileList != null && !fileList.isEmpty()) {
            List<FileEntity> fileEntityList = fileService.createFileList(fileList, FileType.MENU_IMAGE, id);
            fileRepository.saveAll(fileEntityList);
        }

        return id;
    }

    @Transactional(readOnly = true)
    public MenuDetailDto findById(long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));
        List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.MENU_IMAGE, id);

        return MenuDetailDto.create(entity).menuImages(fileEntityList.stream().map(FileDto::of).toList()).build();
    }

    @Transactional(readOnly = true)
    public List<MenuResponseListDto> findAll(MenuConditionDto conditionDto) {
        Pageable pageable = PageRequest.of(conditionDto.getPage(), 10);

        List<MenuListDetailDto> menuDetailDtoList = customMenuRepository.getMenuListByCondition(conditionDto, pageable);

        return menuDetailDtoList.stream().map(menu -> {
            List<FileEntity> menuImages = fileRepository.findAllByTypeAndTargetId(FileType.MENU_IMAGE, menu.getId());
            List<StoreLink> storeLinkList = storeLinkRepository.findAllByStoreId(menu.getStoreId());
            return MenuResponseListDto.create(menu).menuImage(menuImages.stream().map(FileDto::of).toList()).links(storeLinkList.stream().map(StoreLinkDto::of).toList()).build();
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> findByStoreId(Long storeId) {
        List<Menu> menuList = menuRepository.findByStore_Id(storeId);

        return menuList.stream().map(menu -> MenuResponseDto.create(menu).build()).toList();
    }

    @Transactional
    public Long update(Long menuId, MenuRequestDto requestDto, List<MultipartFile> fileList) throws Exception {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + menuId));

        Store store = storeService.findById(requestDto.getStoreId()).toEntity();
        menu.update(requestDto, store);

        fileRepository.deleteAllByTypeAndTargetId(FileType.MENU_IMAGE, menuId);

        if (fileList != null && !fileList.isEmpty()) {
            List<FileEntity> fileEntityList = fileService.createFileList(fileList, FileType.MENU_IMAGE, menuId);
            fileRepository.saveAll(fileEntityList);
        }

        return menuId;
    }

    @Transactional
    public Long delete(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));

        fileRepository.deleteAllByTypeAndTargetId(FileType.MENU_IMAGE, menu.getId());
        menuRepository.delete(menu);
        return id;
    }

    @Transactional
    public long createBookmark(long id, User user) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));
        MenuBookmark menuBookmark = MenuBookmark.builder()
                .menu(menu)
                .user(user)
                .build();
        MenuBookmark newEntity = menuBookmarkRepository.save(menuBookmark);
        return newEntity.getId();
    }

    @Transactional
    public long deleteBookmark(long id, User user) {
        MenuBookmark menuBookmark = menuBookmarkRepository.findByUserIdAndMenuId(user.getId(), id);
        menuBookmarkRepository.delete(menuBookmark);
        return menuBookmark.getId();
    }

}
