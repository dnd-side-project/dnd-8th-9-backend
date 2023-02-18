package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.StoreDto;
import com.team9ookie.dangdo.dto.menu.MenuRequestDto;
import com.team9ookie.dangdo.dto.menu.MenuResponseDto;
import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
import com.team9ookie.dangdo.repository.MenuRepository;
import com.team9ookie.dangdo.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final StoreService storeService;
    @Transactional
    public long save(MenuRequestDto requestDto, long storeId){
        Store store = storeService.get(storeId).toEntity();
        return menuRepository.save(requestDto.toEntity(store)).getId();
    }

    @Transactional(readOnly = true)
    public MenuResponseDto findById(long id) {
        Menu entity = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));

        return new MenuResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> findAll(Long storeId) {
        return menuRepository.findByStore_Id(storeId).stream().map(MenuResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long menuId, Long storeId, MenuRequestDto requestDto){
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + menuId));

        Store store = storeService.get(storeId).toEntity();
        menu.update(requestDto.getName(),requestDto.getPrice(),requestDto.getCaution(),requestDto.getDescription(),requestDto.getCategory(),store);

        return menuId;
    }

    @Transactional
    public void delete (Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다. id=" + id));

        menuRepository.delete(menu);
    }
}
