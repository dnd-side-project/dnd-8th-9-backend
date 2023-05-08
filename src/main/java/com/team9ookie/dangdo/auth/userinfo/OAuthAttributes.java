package com.team9ookie.dangdo.auth.userinfo;

import com.team9ookie.dangdo.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nickname;
    private String email;
    private String profileImg;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nickname, String email, String profileImg) {
        this.attributes = attributes;
        this.nickname = nickname;
        this.email = email;
        this.profileImg = profileImg;
    }

    public static OAuthAttributes of(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> KakaoAccountImg = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .nickname((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .profileImg((String) KakaoAccountImg.get("profile_image"))
                .attributes(attributes)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .profileImg(profileImg)
                .active(true)
                .build();
    }
}