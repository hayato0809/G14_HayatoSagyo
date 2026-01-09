package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;
import dto.CategoryDTO;

public class CategoryDAO {

    public List<CategoryDTO> findAllActive() throws SQLException {
        List<CategoryDTO> list = new ArrayList<>();

        String sql = """
            SELECT CATEGORY_ID, CATEGORY_NAME
            FROM MENU_CATEGORIES
            WHERE IS_ACTIVE = 1
            ORDER BY SORT_ORDER, CATEGORY_ID
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new CategoryDTO(
                        rs.getInt("CATEGORY_ID"),
                        rs.getString("CATEGORY_NAME")
                ));
            }
        }
        return list;
    }
}
