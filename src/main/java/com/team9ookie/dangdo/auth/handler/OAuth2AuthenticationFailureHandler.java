package com.team9ookie.dangdo.auth.handler;

import com.team9ookie.dangdo.utils.CookieUtil;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.team9ookie.dangdo.utils.CookieUtil.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        exception.printStackTrace();

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}