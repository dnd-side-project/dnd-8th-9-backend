package com.team9ookie.dangdo.dto.store;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Platform {

    INSTAGRAM,

    KAKAO,

    NAVER;

    public static Platform findByName(String name) {
        Optional<Platform> platform = Arrays.stream(Platform.values()).filter(v -> v.name().equals(name)).findFirst();
        return platform.orElseThrow(() -> new IllegalArgumentException("해당 이름의 플랫폼이 없습니다. name: " + name));
    }

}
