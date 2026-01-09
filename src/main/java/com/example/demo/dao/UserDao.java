package com.example.demo.dao;

import com.example.demo.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;


public class UserDao {
private static final String URL = "jdbc:mysql://localhost:3306/sample_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // IDでユーザー情報を取得
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return Optional.of(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // ユーザー情報を更新
    public void update(User user) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setLong(3, user.getId());
            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // ユーザー情報を削除
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // メールアドレス重複チェック
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // メールアドレスとパスワードでユーザー情報を取得
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

