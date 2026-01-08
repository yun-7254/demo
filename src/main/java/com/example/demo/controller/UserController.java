package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // マイページ表示
    @GetMapping("/user")
    public String userPage(HttpSession session, Model model) {

        // セッションからログインユーザーを取得
        User user = (User) session.getAttribute("loginUser");

        // 未ログインの場合
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "user";
    }

    // ユーザー情報更新
    @PostMapping("/user/update")
    public String update(User user, HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        // フォームの値で更新
        loginUser.setName(user.getName());
        loginUser.setEmail(user.getEmail());

        userRepository.save(loginUser);

        // セッションの情報も更新
        session.setAttribute("loginUser", loginUser);

        return "redirect:/user";
    }

    // 退会処理
    @PostMapping("/user/delete")
    public String delete(HttpSession session) {

        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        userRepository.deleteById(user.getId());
        session.invalidate();

        return "redirect:/login";
    }
}
