package com.team9ookie.dangdo.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum GoodPoint {

    TASTE("맛있어요"),

    MONEY("가성비가 좋아요"),

    KINDNESS("친절해요"),

    GIFT("선물하기 좋아요"),

    RESPONSE("응답이 빨라요");

    private final String message;

    public static GoodPoint findByMessage(String message) {
        Optional<GoodPoint> goodPoint = Arrays.stream(values()).filter(v -> v.getMessage().equals(message)).findFirst();
        return goodPoint.orElseThrow(() -> new IllegalArgumentException("해당 메시지의 굿포인트가 없습니다. 메시지: " + message));
    }

}
