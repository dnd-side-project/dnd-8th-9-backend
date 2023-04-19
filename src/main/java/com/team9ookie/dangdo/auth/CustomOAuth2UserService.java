package com.team9ookie.dangdo.auth;

import com.team9ookie.dangdo.auth.userinfo.OAuthAttributes;
import com.team9ookie.dangdo.auth.userinfo.PrincipalDetails;
import com.team9ookie.dangdo.entity.User;
import com.team9ookie.dangdo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); // Oauth2 정보를 가져옴

        OAuthAttributes attributes = OAuthAttributes.of(oAuth2User.getAttributes());

        User user = userRepository.findByEmail(attributes.getEmail());

        //DB에 없는 사용자라면 회원가입처리
        if (user == null) {
            user = saveOrUpdate(attributes);
        }

        return new PrincipalDetails(user, attributes.getAttributes());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = attributes.toEntity();

        return userRepository.save(user);
    }
}