package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.dto.review.ReviewRequestDto;
import com.team9ookie.dangdo.dto.review.ReviewResponseDto;
import com.team9ookie.dangdo.entity.*;
import com.team9ookie.dangdo.repository.FileRepository;
import com.team9ookie.dangdo.repository.MenuRepository;
import com.team9ookie.dangdo.repository.ReviewRepository;
import com.team9ookie.dangdo.repository.StoreRepository;
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

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;

    private final FileRepository fileRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream().map(review -> {
            ReviewResponseDto reviewResponseDto = ReviewResponseDto.of(review);
            List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.REVIEW_IMAGE, review.getId());
            reviewResponseDto.setReviewImages(fileEntityList.stream().map(FileDto::of).toList());
            return reviewResponseDto;
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByStoreId(long storeId) {
        List<Review> reviewList = reviewRepository.findByStoreId(storeId);
        return reviewList.stream().map(review -> {
            ReviewResponseDto reviewResponseDto = ReviewResponseDto.of(review);
            List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.REVIEW_IMAGE, review.getId());
            reviewResponseDto.setReviewImages(fileEntityList.stream().map(FileDto::of).toList());
            return reviewResponseDto;
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByMenuId(long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("없는 메뉴입니다. id=" + menuId));
        String menuName = menu.getName();
        List<Review> reviewList = reviewRepository.findByMenuId(menuId);
        return reviewList.stream().map(review -> {
            ReviewResponseDto reviewResponseDto = ReviewResponseDto.of(review);
            reviewResponseDto.setMenuName(menuName);
            List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.REVIEW_IMAGE, review.getId());
            reviewResponseDto.setReviewImages(fileEntityList.stream().map(FileDto::of).toList());
            return reviewResponseDto;
        }).toList();
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto findById(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다. id: " + id));
        String menuName = review.getMenu().getName();
        ReviewResponseDto reviewResponseDto = ReviewResponseDto.of(review);
        reviewResponseDto.setMenuName(menuName);
        List<FileEntity> fileEntityList = fileRepository.findAllByTypeAndTargetId(FileType.REVIEW_IMAGE, review.getId());
        reviewResponseDto.setReviewImages(fileEntityList.stream().map(FileDto::of).toList());
        return reviewResponseDto;
    }

    @Transactional
    public long create(ReviewRequestDto dto, User user) throws IOException {
        List<MultipartFile> fileList = dto.getFiles();
        long menuId = dto.getMenuId();
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. id: " + menuId));
        Store store = menu.getStore();
        Review review = reviewRepository.save(dto.toEntity(store, menu, user));

        List<FileEntity> fileEntityList = fileService.createFileList(fileList, FileType.REVIEW_IMAGE, review.getId());
        fileRepository.saveAll(fileEntityList);

        return review.getId();
    }

    @Transactional
    public long delete(long id, User user) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. id: " + id));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 리뷰가 아닙니다.");
        }
        reviewRepository.deleteById(review.getId());
        return review.getId();
    }

}
