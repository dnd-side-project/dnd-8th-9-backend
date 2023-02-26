package com.team9ookie.dangdo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseResponseDto<T> {

    private final int status;
    private final T data;
    private final String message;

    public static <T> BaseResponseDto<T> ok(T data) {
        return new BaseResponseDto<>(200, data, null);
    }

    public static <T> BaseResponseDto<T> badRequest(String message) {
        return new BaseResponseDto<>(400, null, message);
    }

    public static <T> BaseResponseDto<T> forbidden(String message) {
        return new BaseResponseDto<>(403, null, message);
    }

    public static <T> BaseResponseDto<T> internalServerError(String message) {
        return new BaseResponseDto<>(500, null, message);
    }

}
