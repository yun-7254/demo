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
            model.addAttribute("error", "必須項目を入力してください");
            return "register";
        }

        // メールアドレス重複チェック
        if (userRepository.existsByEmail(user.getEmail())) {
            // 重複の場合
            model.addAttribute("このメールアドレスは既に登録されています。");
            return "register";
        }

        // ユーザーを保存
        userRepository.save(user);

        // 登録後はログイン画面へ
        return "redirect:/login";
    }
}
