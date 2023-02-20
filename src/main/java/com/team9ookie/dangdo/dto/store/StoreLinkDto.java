package com.team9ookie.dangdo.dto.store;

import com.team9ookie.dangdo.entity.StoreLink;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreLinkDto {

    private long id;

    private String platform;

    private String url;

    public StoreLink toEntity() {
        return StoreLink.builder()
                .id(id)
                .platform(Platform.findByName(platform))
                .url(url)
                .build();
    }

    public static StoreLinkDto of(StoreLink storeLink) {
        return StoreLinkDto.builder()
                .id(storeLink.getId())
                .platform(storeLink.getPlatform().name())
                .url(storeLink.getUrl())
                .build();
    }

}
