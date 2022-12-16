package com.ohohchoo.global.config.interceptor;

import com.ohohchoo.domain.user.global.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private static final String HEADER_AUTH = "access-token";
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub

        if (request.getMethod().equals("OPTIONS"))
            return true;

        final String token = request.getHeader(HEADER_AUTH);

        if (token != null) {
            jwtUtil.valid(token);
            return true;
        }
        throw new Exception("유효하지 않은 접근입니다.");
    }

}