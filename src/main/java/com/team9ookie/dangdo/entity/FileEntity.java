package com.team9ookie.dangdo.entity;

import com.team9ookie.dangdo.dto.file.FileType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "file")
public class FileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private FileType type;

    @Column
    private String url;

    @Column
    private long targetId;

}
