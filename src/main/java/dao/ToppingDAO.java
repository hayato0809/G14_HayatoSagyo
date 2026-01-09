package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;
import dto.ToppingDTO;

public class ToppingDAO {

    public List<ToppingDTO> findAllActive() throws SQLException {
        List<ToppingDTO> list = new ArrayList<>();

        String sql = """
            SELECT TOPPING_ID, TOPPING_NAME, PRICE_YEN
            FROM TOPPINGS
            WHERE IS_ACTIVE = 1
            ORDER BY SORT_ORDER, TOPPING_ID
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ToppingDTO(
                        rs.getInt("TOPPING_ID"),
                        rs.getString("TOPPING_NAME"),
                        rs.getInt("PRICE_YEN")
                ));
            }
        }
        return list;
    }
}
