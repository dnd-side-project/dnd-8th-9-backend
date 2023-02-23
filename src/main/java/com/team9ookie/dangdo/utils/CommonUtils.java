package com.team9ookie.dangdo.utils;

import java.util.Optional;

public class CommonUtils {
    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

}
