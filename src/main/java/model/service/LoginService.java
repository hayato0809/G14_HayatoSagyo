package model.service;

import dao.UserDAO;

public class LoginService {

    private UserDAO dao = new UserDAO();

    /**
     * ID が存在するか確認
     */
    public boolean existsUser(String loginId) {
        return dao.exists(loginId);
    }

    /**
     * 指定された ID のパスワードを取得
     */
    public String getPassword(String loginId) {
        return dao.findPassword(loginId);
    }
}
