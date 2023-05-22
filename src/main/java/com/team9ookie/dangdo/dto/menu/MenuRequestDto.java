package com.team9ookie.dangdo.dto.menu;

import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Store;
import lombok.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private String name;
    private int price;
    private long storeId;
    private String description;
    private Map<String,String> basicInfo;
    private String detailInfo;
    private List<String> category;
    private List<MultipartFile> fileList;

    public Menu toEntity(Store store) {
        return Menu.builder().name(name).price(price).basicInfo(basicInfo).detailInfo(detailInfo).category(String.join(",",category)).store(store).description(description).build();
    }
}
