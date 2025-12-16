package model.service;

import dao.UserDAO;

public class LoginService {

    private UserDAO dao = new UserDAO();

    /** ID が存在するか */
    public boolean existsUser(String loginId) {
        return dao.exists(loginId);
    }

    /** パスワードが正しいか判定 */
    public boolean checkPassword(String loginId, String inputPass) {
        String dbPass = dao.findPassword(loginId);
        if (dbPass == null) return false;

        return dbPass.equals(inputPass);
    }
}
