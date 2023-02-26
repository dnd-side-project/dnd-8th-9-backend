package com.team9ookie.dangdo.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    STORE_IMAGE("stores"),

    MENU_IMAGE("menus"),

    REVIEW_IMAGE("reviews");

    private final String filePath;

}
