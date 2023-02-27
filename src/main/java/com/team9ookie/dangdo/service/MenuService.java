package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.dto.menu.MenuDetailDto;
import com.team9ookie.dangdo.dto.menu.MenuRequestDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseDto;
import com.team9ookie.dangdo.entity.FileEntity;
import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
import com.team9ookie.dangdo.repository.FileRepository;
import com.team9ookie.dangdo.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final StoreService storeService;
    private final FileService fileService;

    @Transactional
    public Long save(MenuRequestDto requestDto, long storeId, List<MultipartFile> fileList) throws Exception {
        Store store = storeService.get(storeId).toEntity();
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
    public List<MenuResponseDto> findAll(Long storeId) {
        List<Menu> menuList = menuRepository.findByStore_Id(storeId);

        return menuList.stream().map(menu -> {
            List<FileEntity> menuImages = fileRepository.findAllByTypeAndTargetId(FileType.MENU_IMAGE, menu.getId());
            return MenuResponseDto.create(menu).menuImages(menuImages.stream().map(FileDto::of).toList()).build();
        }).toList();
    }

    @Transactional
    public Long update(Long menuId, Long storeId, MenuRequestDto requestDto, List<MultipartFile> fileList) throws Exception {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + menuId));

        Store store = storeService.get(storeId).toEntity();
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
}
