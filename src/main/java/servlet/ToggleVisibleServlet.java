package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/menu/toggleVisible")
public class ToggleVisibleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ログインチェック（loginId方式に統一）
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginId") == null) {
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        int itemId = Integer.parseInt(req.getParameter("itemId"));

        // どのカテゴリ表示中だったか保持して戻す
        String categoryId = req.getParameter("categoryId");
        if (categoryId == null) categoryId = "0";

        try {
            new MenuItemDAO().toggleVisible(itemId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/menu/list?categoryId=" + categoryId);
    }
}
