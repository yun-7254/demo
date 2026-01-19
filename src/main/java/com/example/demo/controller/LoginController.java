package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.dao.UserDao;
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
    public String login() {
        return "login";
    }

    // ログイン処理
    @PostMapping("/login")
    public String loginProcess(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session
    ) {

        // メールとパスワードでユーザーを探す
        User user = userDao.findByEmailAndPassword(email, password);

        if (user != null) {
            // session には userId のみ保存
            session.setAttribute("loginUserId", user.getId());
            return "redirect:/user";
        }

        // 見つからなかった場合はエラー表示
        session.setAttribute("loginError", "ログインに失敗しました");
        return "login";
    }
}
