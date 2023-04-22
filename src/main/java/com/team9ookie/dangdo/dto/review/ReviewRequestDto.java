package com.team9ookie.dangdo.dto.review;

import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Review;
import com.team9ookie.dangdo.entity.Store;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {

    private String content;

    private int dangdo;

    private String goodPoint;

    private boolean reorder;

    private List<MultipartFile> files;

    private long storeId;

    private long menuId;

    public Review toEntity(Store store, Menu menu) {
        return Review.builder()
                .content(content)
                .dangdo(dangdo)
                .goodPoint(GoodPoint.findByMessage(goodPoint))
                .reorder(isReorder())
                .store(store)
                .menu(menu)
                .build();
    }

}
