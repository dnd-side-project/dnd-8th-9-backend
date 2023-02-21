package com.team9ookie.dangdo.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GoodPoint {

    TASTE("맛있어요"),

    MONEY("가성비가 좋아요"),

    KINDNESS("친절해요"),

    GIFT("선물하기 좋아요"),

    RESPONSE("응답이 빨라요");

    private final String message;

}
