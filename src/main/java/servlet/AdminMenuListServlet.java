package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.CategoryDAO;
import dao.MenuItemDAO;
import dto.CategoryDTO;
import dto.MenuItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/menu/list")
public class AdminMenuListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // ログイン必須にしたいならここでチェック（loginUser想定）
    	HttpSession session = req.getSession(false);
    	if (session == null || session.getAttribute("loginId") == null) {
    	    resp.sendRedirect(req.getContextPath() + "/Login");
    	    return;
    	}


        int categoryId = 0;
        String cat = req.getParameter("categoryId");
        if (cat != null && !cat.isBlank()) {
            try { categoryId = Integer.parseInt(cat); } catch (NumberFormatException ignored) {}
        }

        try {
            CategoryDAO categoryDAO = new CategoryDAO();
            MenuItemDAO itemDAO = new MenuItemDAO();

            List<CategoryDTO> categories = categoryDAO.findAllActive();
            List<MenuItemDTO> items = itemDAO.findForList(categoryId == 0 ? null : categoryId);

            req.setAttribute("categories", categories);
            req.setAttribute("items", items);
            req.setAttribute("selectedCategoryId", categoryId);

            req.getRequestDispatcher("/WEB-INF/jsp/admin_menu_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
