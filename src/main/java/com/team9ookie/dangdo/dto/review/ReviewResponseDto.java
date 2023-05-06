package com.team9ookie.dangdo.dto.review;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.entity.Review;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private long id;

    private String menuName;

    private String content;

    private int dangdo;

    private String goodPoint;

    private boolean reorder;

    private long storeId;

    private String storeName;

    private String nickname;

    private List<FileDto> reviewImages;

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .menuName(review.getMenu().getName())
                .content(review.getContent())
                .dangdo(review.getDangdo())
                .goodPoint(review.getGoodPoint().getMessage())
                .reorder(review.isReorder())
                .storeId(review.getStore().getId())
                .storeName(review.getStore().getName())
                .nickname(review.getUser().getNickname())
                .build();
    }

}
