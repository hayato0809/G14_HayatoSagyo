package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBManager;

public class UserDAO {

    /**
     * 指定されたIDが既に LOGINUSERS テーブルに存在するか確認
     */
    public boolean exists(String loginId) {
        String sql = "SELECT COUNT(*) FROM LOGINUSERS WHERE LOGIN_ID = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * LOGINUSERS テーブルに新規ユーザー登録
     */
    public int insert(String loginId, String password) {
        String sql = "INSERT INTO LOGINUSERS (LOGIN_ID, PASSWORD, CREATED_AT, UPDATED_AT) "
                   + "VALUES (?, ?, SYSDATE, SYSDATE)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, loginId);
            ps.setString(2, password);

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 指定された LOGIN_ID のパスワードを取得
     */
    public String findPassword(String loginId) {
        String sql = "SELECT PASSWORD FROM LOGINUSERS WHERE LOGIN_ID = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("PASSWORD");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
