//package com.helloworld.demo.config;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 退出
// *
// * @author wangzh 2019/02/17 21:15
// */
//
//public class CustomLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {
//
//    private static final String BEARER_AUTHENTICATION = "Bearer ";
//    private static final String HEADER_AUTHORIZATION = "authorization";
//
//    @Autowired
//    private TokenStore tokenStore;
//
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        String token = request.getHeader(HEADER_AUTHORIZATION);
//        if (!StringUtils.isEmpty(token) && token.startsWith(BEARER_AUTHENTICATION)) {
//            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[0]);
//            if (oAuth2AccessToken != null) {
//                tokenStore.removeAccessToken(oAuth2AccessToken);
//            }
//        }
//    }
//}
