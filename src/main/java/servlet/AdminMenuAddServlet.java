package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.CategoryDAO;
import dao.MenuItemDAO;
import dao.ToppingDAO;
import dto.CategoryDTO;
import dto.ToppingDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/menu/add")
public class AdminMenuAddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ログインチェック（loginId方式に統一）
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginId") == null) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        try {
            List<CategoryDTO> categories = new CategoryDAO().findAllActive();
            List<ToppingDTO> toppings = new ToppingDAO().findAllActive();

            req.setAttribute("categories", categories);
            req.setAttribute("toppings", toppings);

            req.getRequestDispatcher("/WEB-INF/jsp/admin_menu_add.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // ログインチェック（loginId方式に統一）
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginId") == null) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        // パラメータ取得
        String categoryIdStr = req.getParameter("categoryId");
        String itemName = req.getParameter("itemName");
        String priceStr = req.getParameter("priceYen");
        String[] toppingIds = req.getParameterValues("toppingIds");

        // 入力チェック（最低限）
        int categoryId;
        int priceYen;

        try {
            categoryId = Integer.parseInt(categoryIdStr);
            priceYen = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "カテゴリーと値段を正しく入力してくれ");
            doGet(req, resp);
            return;
        }

        if (itemName == null || itemName.isBlank()) {
            req.setAttribute("error", "商品名を入力してくれ");
            doGet(req, resp);
            return;
        }

        try {
            MenuItemDAO dao = new MenuItemDAO();

            // 1) 商品INSERT
            int itemId = dao.insertMenuItem(categoryId, itemName.trim(), priceYen);

            // 2) トッピング紐付け（チェックされた分だけ）
            if (toppingIds != null) {
                for (String tId : toppingIds) {
                    dao.insertItemTopping(itemId, Integer.parseInt(tId));
                }
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // 追加後は一覧へ戻る
        resp.sendRedirect(req.getContextPath() + "/admin/menu/list");
    }
}
