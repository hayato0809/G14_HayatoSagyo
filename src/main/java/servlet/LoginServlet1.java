package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.service.LoginService;

@WebServlet("/Login")
public class LoginServlet1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private LoginService service = new LoginService();

    /**
     * ログイン画面表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp")
               .forward(request, response);
    }

    /**
     * ログイン処理
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        // ---------- 入力チェック ----------
        if ((loginId == null || loginId.isEmpty()) &&
            (password == null || password.isEmpty())) {

            request.setAttribute("error", "ID、PASSを入力してください");
            doGet(request, response);
            return;
        }

        if (loginId == null || loginId.isEmpty()) {
            request.setAttribute("error", "IDを入力してください");
            doGet(request, response);
            return;
        }

        if (password == null || password.isEmpty()) {
            request.setAttribute("error", "パスワードを入力してください");
            doGet(request, response);
            return;
        }

        // ---------- 認証処理 ----------
        boolean exists = service.existsUser(loginId);

        if (!exists) {
            request.setAttribute("error", "idが違います");
            doGet(request, response);
            return;
        }

        String correctPass = service.getPassword(loginId);

        if (!correctPass.equals(password)) {
            request.setAttribute("error", "パスワードが違います");
            doGet(request, response);
            return;
        }

        // ---------- ログイン成功 ----------
        HttpSession session = request.getSession();
        session.setAttribute("loginId", loginId); // ← ★ここが超重要★

        response.sendRedirect(request.getContextPath() + "/Top");
    }
}
