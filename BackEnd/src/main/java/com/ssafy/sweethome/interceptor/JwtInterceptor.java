package com.ssafy.sweethome.interceptor;

import com.ssafy.sweethome.exception.UnAuthorizedException;
import com.ssafy.sweethome.user.model.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "auth-token";
    private final JwtService jwtService;

    @Autowired
    public JwtInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        final String token = request.getHeader(HEADER_AUTH);
//
//        if (token != null && jwtService.checkToken(token)) {
//            log.info("valid token : {}", token);
//            return true;
//        } else {
//            log.info("invalid token : {}", token);
//            throw new UnAuthorizedException();
//        }
//    }
}
