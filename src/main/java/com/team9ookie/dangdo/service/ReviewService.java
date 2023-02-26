package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.review.ReviewRequestDto;
import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.entity.FileEntity;
import com.team9ookie.dangdo.entity.Menu;
import com.team9ookie.dangdo.entity.Review;
import com.team9ookie.dangdo.repository.FileRepository;
import com.team9ookie.dangdo.repository.MenuRepository;
import com.team9ookie.dangdo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final FileService fileService;

    private final ReviewRepository reviewRepository;

    private final MenuRepository menuRepository;

    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getAll(long menuId) {
        List<Review> reviewList = reviewRepository.findByMenuId(menuId);
        return reviewList.stream().map(review -> {
            ReviewResponseDto reviewResponseDto = ReviewResponseDto.of(review);
            List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.REVIEW_IMAGE, review.getId());
            reviewResponseDto.setReviewImages(fileEntityList.stream().map(FileDto::of).toList());
            return reviewResponseDto;
        }).toList();
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto get(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다. id: " + reviewId));
        ReviewResponseDto reviewResponseDto = ReviewResponseDto.of(review);
        List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.REVIEW_IMAGE, review.getId());
        reviewResponseDto.setReviewImages(fileEntityList.stream().map(FileDto::of).toList());
        return reviewResponseDto;
    }

    @Transactional
    public long create(long menuId, ReviewRequestDto dto, List<MultipartFile> fileList) throws IOException {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. id: " + menuId));
        Review review = reviewRepository.save(dto.toEntity(menu));

        List<FileEntity> fileEntityList = fileService.createFileList(fileList, FileType.REVIEW_IMAGE, review.getId());
        fileRepository.saveAll(fileEntityList);

        return review.getId();
    }

    @Transactional
    public long delete(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id: " + id));
        reviewRepository.deleteById(review.getId());
        return review.getId();
    }

}
