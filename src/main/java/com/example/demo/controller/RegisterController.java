package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private UserDao userDao;

    public RegisterController(UserDao userDao) {
        this.userDao = userDao;
    }

    // 登録画面表示
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 登録処理
    @PostMapping("/register")
    public String registerProcess(User user, Model model) {

        // 名前未入力チェック
        if (user.getName().isEmpty()){
            model.addAttribute("error", "名前を入力してください");
        return "register";
    }

        // メールアドレス未入力チェック
        if (user.getEmail().isEmpty()){
            model.addAttribute("error", "メールアドレスを入力してください");
            return "register";
    }
        // パスワード未入力チェック
        if (user.getPassword().isEmpty()) {
            model.addAttribute("error", "パスワードを入力してください");
            return "register";
        }

        // メールアドレス重複チェック
        if (userDao.existsByEmail(user.getEmail())) {
            // 重複の場合
            model.addAttribute("error", "このメールアドレスは既に登録されています。");
            return "register";
        }

        // ユーザーを保存
        userDao.insert(user);
        return "redirect:/login";
    }
}
