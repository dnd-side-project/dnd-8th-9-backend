package com.team9ookie.dangdo.service;

import com.team9ookie.dangdo.dto.file.FileDto;
import com.team9ookie.dangdo.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void saveFile(FileDto fileDto) {
        fileRepository.save(fileDto.toEntity());
    }
}
