package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {

    private UserRepository userRepository;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 登録画面表示
    @GetMapping("/register")
    public String register(Model model) {
        // フォーム用にUserを渡す
        model.addAttribute("user", new User());
        return "register";
    }

    // 登録処理
    @PostMapping("/register")
    public String registerProcess(@ModelAttribute User user, Model model) {

        // 名前・メールアドレス・パスワードが未入力の場合
        if (user.getName() == null || user.getName().equals("")
                || user.getEmail() == null || user.getEmail().equals("")
                || user.getPassword() == null || user.getPassword().equals("")) {

            model.addAttribute("error", "必須項目を入力してください");
            return "register";
        }

        // ユーザーを保存
        userRepository.save(user);

        // 登録後はログイン画面へ
        return "redirect:/login";
    }
}
