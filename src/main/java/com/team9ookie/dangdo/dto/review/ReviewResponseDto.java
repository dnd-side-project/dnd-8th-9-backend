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

    private String content;

    private int dangdo;

    private String goodPoint;

    private boolean reorder;

    private List<FileDto> reviewImages;

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .dangdo(review.getDangdo())
                .goodPoint(review.getGoodPoint().getMessage())
                .reorder(review.isReorder())
                .build();
    }

}
