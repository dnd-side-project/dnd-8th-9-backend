package com.team9ookie.dangdo.dto;

import com.team9ookie.dangdo.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {

    private long id;
    private FileType type;
    private String url;
    private String targetId;

    public FileEntity toEntity() {
        return FileEntity.builder()
            .id(id)
            .type(FileType.STORE_IMAGE)
            .url(url)
            .targetId(targetId)
            .build();
    }

    public static FileDto of(FileEntity fileEntity) {
        return FileDto.builder()
            .id(fileEntity.getId())
            .type(fileEntity.getType())
            .url(fileEntity.getUrl())
            .targetId(fileEntity.getTargetId())
            .build();
    }

}
