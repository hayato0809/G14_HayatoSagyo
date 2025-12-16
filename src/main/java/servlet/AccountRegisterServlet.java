package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.service.UserService;

@WebServlet("/AccountRegister")
public class AccountRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService service = new UserService();

    /**
     * ユーザー登録画面表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/register.jsp")
               .forward(request, response);
    }

    /**
     * ユーザー登録処理
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        // -------- 入力チェック --------
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

        // -------- ID重複チェック --------
        if (service.existsUser(loginId)) {
            request.setAttribute("error", "使用済みIDです");
            doGet(request, response);
            return;
        }

        // -------- 登録処理 --------
        boolean result = service.registerUser(loginId, password);

        if (!result) {
            request.setAttribute("error", "登録に失敗しました");
            doGet(request, response);
            return;
        }

        // -------- 登録成功 --------
        request.getRequestDispatcher("/WEB-INF/jsp/registerComplete.jsp")
               .forward(request, response);
    }
}
