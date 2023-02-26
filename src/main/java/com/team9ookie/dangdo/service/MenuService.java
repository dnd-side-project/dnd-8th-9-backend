package com.team9ookie.dangdo.service;

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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final FileRepository fileRepository;
    private final S3Service s3Service;
    private final StoreService storeService;
    @Transactional
    public Long save(MenuRequestDto requestDto, long storeId, MultipartFile menuImg){
        Store store = storeService.get(storeId).toEntity();
        Long id = menuRepository.save(requestDto.toEntity(store)).getId();
        //Todo global exception 처리
        try {
            String filePath = s3Service.upload(menuImg,"menu");
            FileEntity file = FileEntity.builder().type(FileType.MENU_IMAGE).url(filePath).targetId(id).build();
            fileRepository.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Transactional(readOnly = true)
    public MenuDetailDto findById(long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));

        return MenuDetailDto.of(entity);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> findAll(Long storeId) {
        return menuRepository.findByStore_Id(storeId).stream().map(MenuResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long menuId, Long storeId, MenuRequestDto requestDto, MultipartFile menuImg){
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + menuId));

        Store store = storeService.get(storeId).toEntity();
        menu.update(requestDto,store);

        try {
            fileRepository.deleteAllByTypeAndTargetId(FileType.MENU_IMAGE,menuId);
            String filePath = s3Service.upload(menuImg,"menu");
            FileEntity file = FileEntity.builder().type(FileType.MENU_IMAGE).url(filePath).targetId(menuId).build();
            fileRepository.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return menuId;
    }

    @Transactional
    public Long delete (Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));

        fileRepository.deleteAllByTypeAndTargetId(FileType.MENU_IMAGE,menu.getId());
        menuRepository.delete(menu);
        return id;
    }
}
