package com.example.demo.entity;

public class User{

    private Long id;
    private String name;
    private String email;
    private String password;


    // ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 名前
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // メールアドレス
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // パスワード
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}