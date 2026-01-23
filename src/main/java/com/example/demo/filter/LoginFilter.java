package com.example.demo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        // ログイン不要ページはそのまま通す
        if (uri.equals("/login")
                || uri.equals("/register")
                || uri.equals("/error")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Long loginUserId = (session == null)
                ? null
                : (Long) session.getAttribute("loginUserId");

        // 未ログインならログイン画面へ
        if (loginUserId == null) {
            res.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
