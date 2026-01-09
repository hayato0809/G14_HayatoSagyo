package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBManager;
import dto.MenuItemDTO;

public class MenuItemDAO {

    /* =====================================================
     * 一覧取得（カテゴリ指定 / 全て）
     * ===================================================== */
    public List<MenuItemDTO> findForList(Integer categoryId) throws SQLException {

        List<MenuItemDTO> list = new ArrayList<>();

        String baseSql = """
            SELECT
              i.ITEM_ID,
              i.CATEGORY_ID,
              c.CATEGORY_NAME,
              i.ITEM_NAME,
              i.PRICE_YEN,
              i.IS_VISIBLE
            FROM MENU_ITEMS i
            JOIN MENU_CATEGORIES c
              ON i.CATEGORY_ID = c.CATEGORY_ID
        """;

        String where = (categoryId == null || categoryId == 0)
                ? ""
                : " WHERE i.CATEGORY_ID = ? ";

        String order = " ORDER BY c.SORT_ORDER, i.ITEM_ID ";

        String sql = baseSql + where + order;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (categoryId != null && categoryId != 0) {
                ps.setInt(1, categoryId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new MenuItemDTO(
                            rs.getInt("ITEM_ID"),
                            rs.getInt("CATEGORY_ID"),
                            rs.getString("CATEGORY_NAME"),
                            rs.getString("ITEM_NAME"),
                            rs.getInt("PRICE_YEN"),
                            rs.getInt("IS_VISIBLE")
                    ));
                }
            }
        }
        return list;
    }

    /* =====================================================
     * 表示 / 非表示 トグル（一覧画面）
     * ===================================================== */
    public void toggleVisible(int itemId) throws SQLException {

        String sql = """
            UPDATE MENU_ITEMS
            SET IS_VISIBLE =
              CASE WHEN IS_VISIBLE = 1 THEN 0 ELSE 1 END
            WHERE ITEM_ID = ?
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.executeUpdate();
        }
    }

    /* =====================================================
     * 商品1件取得（編集画面のデフォルト表示）
     * ===================================================== */
    public MenuItemDTO findById(int itemId) throws SQLException {

        String sql = """
            SELECT
              i.ITEM_ID,
              i.CATEGORY_ID,
              c.CATEGORY_NAME,
              i.ITEM_NAME,
              i.PRICE_YEN,
              i.IS_VISIBLE
            FROM MENU_ITEMS i
            JOIN MENU_CATEGORIES c
              ON i.CATEGORY_ID = c.CATEGORY_ID
            WHERE i.ITEM_ID = ?
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MenuItemDTO(
                            rs.getInt("ITEM_ID"),
                            rs.getInt("CATEGORY_ID"),
                            rs.getString("CATEGORY_NAME"),
                            rs.getString("ITEM_NAME"),
                            rs.getInt("PRICE_YEN"),
                            rs.getInt("IS_VISIBLE")
                    );
                }
            }
        }
        return null;
    }

    /* =====================================================
     * 商品追加（INSERT）
     * 追加画面用
     * ===================================================== */
    public int insertMenuItem(int categoryId, String itemName, int priceYen)
            throws SQLException {

        String sql = """
            INSERT INTO MENU_ITEMS (CATEGORY_ID, ITEM_NAME, PRICE_YEN)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, new String[]{"ITEM_ID"})) {

            ps.setInt(1, categoryId);
            ps.setString(2, itemName);
            ps.setInt(3, priceYen);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /* =====================================================
     * 商品更新（編集画面・単体）
     * ※ トランザクション不要な場合用
     * ===================================================== */
    public void updateMenuItem(int itemId, int categoryId, String itemName, int priceYen)
            throws SQLException {

        String sql = """
            UPDATE MENU_ITEMS
            SET CATEGORY_ID = ?,
                ITEM_NAME   = ?,
                PRICE_YEN   = ?
            WHERE ITEM_ID = ?
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setString(2, itemName);
            ps.setInt(3, priceYen);
            ps.setInt(4, itemId);
            ps.executeUpdate();
        }
    }

    /* =====================================================
     * ▼▼▼ ここから「追記してほしい」と言ってた重要ゾーン ▼▼▼
     * ===================================================== */

    /* =====================================================
     * 商品更新（Connection渡し版）
     * 編集画面でトランザクション使う用
     * ===================================================== */
    public void updateMenuItem(Connection con, int itemId, int categoryId,
                               String itemName, int priceYen)
            throws SQLException {

        String sql = """
            UPDATE MENU_ITEMS
            SET CATEGORY_ID = ?,
                ITEM_NAME   = ?,
                PRICE_YEN   = ?
            WHERE ITEM_ID = ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setString(2, itemName);
            ps.setInt(3, priceYen);
            ps.setInt(4, itemId);
            ps.executeUpdate();
        }
    }

    /* =====================================================
     * 商品に紐づくトッピングID一覧取得
     * 編集画面のチェックON判定用
     * ===================================================== */
    public List<Integer> findToppingIdsByItem(int itemId)
            throws SQLException {

        List<Integer> list = new ArrayList<>();

        String sql = """
            SELECT TOPPING_ID
            FROM MENU_ITEM_TOPPINGS
            WHERE ITEM_ID = ?
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("TOPPING_ID"));
                }
            }
        }
        return list;
    }

    /* =====================================================
     * トッピング全削除（編集保存前）
     * ===================================================== */
    public void deleteItemToppings(int itemId) throws SQLException {

        String sql = """
            DELETE FROM MENU_ITEM_TOPPINGS
            WHERE ITEM_ID = ?
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.executeUpdate();
        }
    }

    /* =====================================================
     * トッピング全削除（Connection渡し版）
     * トランザクション用
     * ===================================================== */
    public void deleteItemToppings(Connection con, int itemId)
            throws SQLException {

        String sql = """
            DELETE FROM MENU_ITEM_TOPPINGS
            WHERE ITEM_ID = ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.executeUpdate();
        }
    }

    /* =====================================================
     * トッピング登録（単体）
     * ===================================================== */
    public void insertItemTopping(int itemId, int toppingId)
            throws SQLException {

        String sql = """
            INSERT INTO MENU_ITEM_TOPPINGS (ITEM_ID, TOPPING_ID)
            VALUES (?, ?)
        """;

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setInt(2, toppingId);
            ps.executeUpdate();
        }
    }

    /* =====================================================
     * トッピング登録（Connection渡し版）
     * トランザクション用
     * ===================================================== */
    public void insertItemTopping(Connection con, int itemId, int toppingId)
            throws SQLException {

        String sql = """
            INSERT INTO MENU_ITEM_TOPPINGS (ITEM_ID, TOPPING_ID)
            VALUES (?, ?)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.setInt(2, toppingId);
            ps.executeUpdate();
        }
    }
}
