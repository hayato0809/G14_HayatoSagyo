package model.service;

import dao.UserDAO;

public class UserService {

    private UserDAO dao = new UserDAO();

    /** ID存在チェック */
    public boolean existsUser(String loginId) {
        return dao.exists(loginId);
    }

    /** 新規ユーザー登録 */
    public boolean registerUser(String loginId, String password) {
        return dao.insert(loginId, password) == 1;
    }
}
