package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UserDao userDao;

    public LoginController(UserDao userDao) {
        this.userDao = userDao;
    }

    // ログイン画面表示
    @GetMapping("/login")
    public String login(HttpSession session) {
        session.removeAttribute("loginError");
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String loginProcess(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session
    ) {

        // メールアドレスとパスワードでユーザー検索
        User user = userDao.findByEmailAndPassword(email, password);

        if (user != null) {
            session.setAttribute("loginUserId", user.getId());
            return "redirect:/user";
        }

        // ログイン失敗
        session.setAttribute("loginError", "メールアドレスまたはパスワードが違います");
        return "redirect:/login";
    }
}
