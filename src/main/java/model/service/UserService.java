package model.service;

import dao.UserDAO;

public class UserService {

    private UserDAO dao = new UserDAO();

    /** ID が既に存在するか */
    public boolean existsUser(String loginId) {
        return dao.exists(loginId);
    }

    /** 新規ユーザー登録 */
    public boolean registerUser(String loginId, String password) {
        int result = dao.insert(loginId, password);
        return result == 1;
    }
}
