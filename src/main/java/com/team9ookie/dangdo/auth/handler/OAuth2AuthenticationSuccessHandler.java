package com.team9ookie.dangdo.auth.handler;

import static com.team9ookie.dangdo.auth.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

import com.team9ookie.dangdo.auth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.team9ookie.dangdo.auth.RoleType;
import com.team9ookie.dangdo.auth.token.AuthToken;
import com.team9ookie.dangdo.auth.token.AuthTokenProvider;
import com.team9ookie.dangdo.auth.userinfo.OAuthAttributes;
import com.team9ookie.dangdo.auth.userinfo.PrincipalDetails;
import com.team9ookie.dangdo.config.AppProperties;
import com.team9ookie.dangdo.entity.UserRefreshToken;
import com.team9ookie.dangdo.repository.UserRefreshTokenRepository;
import com.team9ookie.dangdo.utils.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        OAuthAttributes userInfo = OAuthAttributes.of(user.getAttributes());

        RoleType roleType = RoleType.USER;

        Date now = new Date();
        AuthToken accessToken = tokenProvider.createAuthToken(
                userInfo.getEmail(),
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        // refresh 토큰 설정
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // DB 저장
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userInfo.getEmail());
        if (userRefreshToken != null) {
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        } else {
            userRefreshToken = new UserRefreshToken(userInfo.getEmail(), refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

//    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
//        if (authorities == null) {
//            return false;
//        }
//
//        for (GrantedAuthority grantedAuthority : authorities) {
//            if (authority.equals(grantedAuthority.getAuthority())) {
//                return true;
//            }
//        }
//        return false;
//    }

//    private boolean isAuthorizedRedirectUri(String uri) {
//        URI clientRedirectUri = URI.create(uri);
//
//        return appProperties.getOauth2().getAuthorizedRedirectUris()
//            .stream()
//            .anyMatch(authorizedRedirectUri -> {
//                // Only validate host and port. Let the clients use different paths if they want to
//                URI authorizedURI = URI.create(authorizedRedirectUri);
//                if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                    && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//                    return true;
//                }
//                return false;
//            });
//    }
}
