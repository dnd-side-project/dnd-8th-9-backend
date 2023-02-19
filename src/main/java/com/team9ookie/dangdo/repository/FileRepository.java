package com.team9ookie.dangdo.repository;


import com.team9ookie.dangdo.dto.file.FileType;
import com.team9ookie.dangdo.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    void deleteAllByTypeAndTargetId(FileType fileType, long targetId);

    List<FileEntity> findAllByTypeAndTargetId(FileType fileType, long id);
}
