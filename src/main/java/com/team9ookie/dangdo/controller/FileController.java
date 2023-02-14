package com.team9ookie.dangdo.controller;

import com.team9ookie.dangdo.dto.FileDto;
import com.team9ookie.dangdo.service.FileService;
import com.team9ookie.dangdo.service.S3Service;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class FileController {

    private S3Service s3Service;
    private FileService fileService;

    @GetMapping("/file")
    public String dispWrite() {

        return "/file";
    }

    @PostMapping("/file")
    public String execWrite(FileDto fileDto, MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        fileDto.setUrl(imgPath);

        fileService.saveFile(fileDto);

        return "redirect:/file";
    }
}
