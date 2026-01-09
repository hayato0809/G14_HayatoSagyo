package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.CategoryDAO;
import dao.MenuItemDAO;
import dao.ToppingDAO;
import db.DBManager;
import dto.CategoryDTO;
import dto.MenuItemDTO;
import dto.ToppingDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/menu/edit")
public class AdminMenuEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ログインチェック（loginId方式に統一）
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginId") == null) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        String itemIdStr = req.getParameter("itemId");
        if (itemIdStr == null || itemIdStr.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/admin/menu/list");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/menu/list");
            return;
        }

        try {
            MenuItemDAO itemDAO = new MenuItemDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            ToppingDAO toppingDAO = new ToppingDAO();

            MenuItemDTO item = itemDAO.findById(itemId);
            if (item == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/menu/list");
                return;
            }

            List<CategoryDTO> categories = categoryDAO.findAllActive();
            List<ToppingDTO> toppings = toppingDAO.findAllActive();

            List<Integer> selectedIds = itemDAO.findToppingIdsByItem(itemId);
            Set<Integer> selectedSet = new HashSet<>(selectedIds);

            req.setAttribute("item", item);
            req.setAttribute("categories", categories);
            req.setAttribute("toppings", toppings);
            req.setAttribute("selectedToppingIds", selectedSet);

            req.getRequestDispatcher("/WEB-INF/jsp/admin_menu_edit.jsp").forward(req, resp);

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

        String itemIdStr = req.getParameter("itemId");
        String categoryIdStr = req.getParameter("categoryId");
        String itemName = req.getParameter("itemName");
        String priceStr = req.getParameter("priceYen");
        String[] toppingIds = req.getParameterValues("toppingIds");

        int itemId, categoryId, priceYen;

        try {
            itemId = Integer.parseInt(itemIdStr);
            categoryId = Integer.parseInt(categoryIdStr);
            priceYen = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "値が不正だぞ🔥（カテゴリー/値段）");
            req.setAttribute("itemId", itemIdStr);
            doGet(req, resp);
            return;
        }

        if (itemName == null || itemName.isBlank()) {
            req.setAttribute("error", "商品名が空だぞ🔥");
            req.setAttribute("itemId", String.valueOf(itemId));
            doGet(req, resp);
            return;
        }

        try (Connection con = DBManager.getConnection()) {
            con.setAutoCommit(false);

            try {
                MenuItemDAO dao = new MenuItemDAO();

                dao.updateMenuItem(con, itemId, categoryId, itemName.trim(), priceYen);

                dao.deleteItemToppings(con, itemId);

                if (toppingIds != null) {
                    for (String tId : toppingIds) {
                        dao.insertItemTopping(con, itemId, Integer.parseInt(tId));
                    }
                }

                con.commit();

            } catch (Exception ex) {
                con.rollback();
                throw ex;
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/menu/list");
    }
}
