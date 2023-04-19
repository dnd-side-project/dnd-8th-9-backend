package com.team9ookie.dangdo.auth;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.team9ookie.dangdo.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.Assert;

public class OAuth2AuthorizationRequestBasedOnCookieRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public final static String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private final static int cookieExpireSeconds = 180;

    private static final String DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME = HttpSessionOAuth2AuthorizationRequestRepository.class
            .getName() + ".AUTHORIZATION_REQUEST";

    private final String sessionAttributeName = DEFAULT_AUTHORIZATION_REQUEST_ATTR_NAME;


    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Assert.notNull(request, "request cannot be null");
        String stateParameter = getStateParameter(request);
        if (stateParameter == null) {
            return null;
        }
        OAuth2AuthorizationRequest authorizationRequest = getAuthorizationRequest(request);
        return (authorizationRequest != null && stateParameter.equals(authorizationRequest.getState()))
                ? authorizationRequest : null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }
        String state = authorizationRequest.getState();
        Assert.hasText(state, "authorizationRequest.state cannot be empty");
        request.getSession().setAttribute(this.sessionAttributeName, authorizationRequest);


        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(response, "response cannot be null");
        OAuth2AuthorizationRequest authorizationRequest = loadAuthorizationRequest(request);
        if (authorizationRequest != null) {
            request.getSession().removeAttribute(this.sessionAttributeName);
        }
        return authorizationRequest;
    }

    private String getStateParameter(HttpServletRequest request) {
        return request.getParameter(OAuth2ParameterNames.STATE);
    }

    private OAuth2AuthorizationRequest getAuthorizationRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (OAuth2AuthorizationRequest) session.getAttribute(this.sessionAttributeName) : null;
    }
}
