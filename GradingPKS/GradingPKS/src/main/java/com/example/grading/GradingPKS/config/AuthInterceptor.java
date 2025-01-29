package com.example.grading.GradingPKS.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        
        // Allow static resources and login-related paths
        if (path.startsWith("/static") || 
            path.equals("/") || 
            path.equals("/login") || 
            path.equals("/register")) {
            return true;
        }
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
} 