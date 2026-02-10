package com.liu.studentmanagement.config.interceptor;

import com.liu.studentmanagement.common.BaseContext;
import com.liu.studentmanagement.common.enums.RoleEnum;
import com.liu.studentmanagement.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
//            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 1. 从请求头里获取 Token
        // 前端通常放在 "Authorization" 字段中
        String token = request.getHeader("token");

        // 2. 如果 Token 为空
        if (token == null || token.isEmpty()) {
            response.setStatus(401); // 设置状态码为 401 (未授权)
            response.getWriter().write("No token, access denied!");
            return false; // 拦截请求，不让往后走
        }

        try {

            Claims claims = JwtUtils.parseToken(token);// 3. 尝试解析 Token
            Integer userId = (Integer) claims.get("userId");

            String roleCode = claims.get("role").toString();

            if (RoleEnum.getByCode(roleCode) == RoleEnum.STUDENT) {
                String method = request.getMethod();
                if (!"GET".equals(method)) {
                    response.setStatus(403);
                    response.getWriter().write("权限不足：学生账号禁止此操作");
                    return false;
                }
            }
            BaseContext.setCurrentId(userId);
            request.setAttribute("currentUserId", claims.get("userId")); // 🌟 进阶技巧：把解析出来的用户ID存入 request，方便后续 Controller 使用
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.getWriter().write("Invalid or expired token!");
            return false;
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.remove();
    }
}