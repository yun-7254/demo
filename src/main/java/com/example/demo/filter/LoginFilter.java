package com.example.demo.filter;

import com.example.demo.entity.User;
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

        // ログイン画面と登録画面はそのまま表示する
        if (uri.startsWith("/login")
                || uri.startsWith("/register")
                || uri.equals("/error")) {
            chain.doFilter(request, response);
            return;
        }

        // セッションからログインユーザーを取得
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUserId");

        // ログインしていない場合はログイン画面へ
        if (loginUser == null) {
            res.sendRedirect("/login");
            return;
        }

        // 問題なければ次の処理へ
        chain.doFilter(request, response);
    }
}
