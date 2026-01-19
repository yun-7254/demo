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
        if (uri.startsWith("/login")
                || uri.startsWith("/register")
                || uri.equals("/error")) {
            chain.doFilter(request, response);
            return;
        }

        // セッションが既にあるか確認
        HttpSession session = req.getSession(false);

        
        // セッションが無い=未ログイン
        if (session == null) {
            res.sendRedirect("/login");
            return;
        }

        // ログイン済みか確認
        Long loginUserId = (Long) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            res.sendRedirect("/login");
            return;
        }

        // 問題なければ次の処理へ
        chain.doFilter(request, response);
    }
}
