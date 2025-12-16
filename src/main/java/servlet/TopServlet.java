package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Top")
public class TopServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * トップ画面表示（未ログイン防止あり）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 🔒 未ログインチェック
        if (session == null || session.getAttribute("loginId") == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // ログイン済み → トップ画面表示
        request.getRequestDispatcher("/WEB-INF/jsp/top.jsp")
               .forward(request, response);
    }

    /**
     * ログアウト処理
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // セッション破棄
        }

        response.sendRedirect(request.getContextPath() + "/Login");
    }
}
