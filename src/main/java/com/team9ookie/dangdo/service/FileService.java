package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.entity.FileEntity;
import com.team9ookie.dangdo.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final S3Service s3Service;

    public void saveFile(FileDto fileDto) {
        fileRepository.save(fileDto.toEntity());
    }

    public List<FileEntity> createFileList(List<MultipartFile> fileList, FileType fileType, long targetId) throws IOException {
        List<FileEntity> fileEntityList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            String url = s3Service.upload(file, fileType.getFilePath());
            fileEntityList.add(FileEntity.builder()
                    .type(fileType)
                    .url(url)
                    .targetId(targetId)
                    .build());
        }
        return fileEntityList;
    }

}
