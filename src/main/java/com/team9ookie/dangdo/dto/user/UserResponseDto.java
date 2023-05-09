package com.team9ookie.dangdo.dto.user;

import com.team9ookie.dangdo.auth.RoleType;
import com.team9ookie.dangdo.entity.User;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    @Id
    private Long id;
    private String email;
    private String nickname;
    private String profileImg;
    private boolean active;
    private RoleType roleType;

    public static UserResponseDto.UserResponseDtoBuilder create(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImg(user.getProfileImg())
                .active(user.isActive())
                .roleType(user.getRoleType());
    }
}
