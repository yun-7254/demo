package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    // マイページ表示
    @GetMapping("/user")
    public String userPage(HttpSession session, Model model) {

        // セッションからIDを取得
        Long userId = (Long) session.getAttribute("loginUserId");

        // 未ログインの場合
        if (userId == null) {
            return "redirect:/login";
        }

        // DBからユーザー情報を取得
        User user = userDao.findById(userId).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "user";
    }

    // ユーザー情報更新
    @PostMapping("/user/update")
    public String update(User fromUser, HttpSession session) {

        Long userId = (Long) session.getAttribute("loginUserId");
        if (userId == null) {
            return "redirect:/login";
        }

        // DBから最新の情報を取得
        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // フォームの値で更新
        user.setName(fromUser.getName());
        user.setEmail(fromUser.getEmail());

        // DBを更新
        userDao.update(user);

        return "redirect:/user";

    }

    // 退会処理
    @PostMapping("/user/delete")
    public String delete(HttpSession session) {

        Long userId = (Long) session.getAttribute("loginUserId");
        if (userId == null) {
            return "redirect:/login";
        }

        userDao.deleteById(userId);
        session.invalidate();

        return "redirect:/login";
    }
}
