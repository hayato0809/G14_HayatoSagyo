<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<style>
    body {
        font-family: sans-serif;
        text-align: center;
        margin-top: 80px;
    }
    .input-box {
        width: 300px;
        height: 35px;
        background: #ddd;
        border: none;
        margin-bottom: 25px;
        padding: 5px;
        font-size: 16px;
    }
    .btn {
        width: 200px;
        height: 40px;
        margin: 10px 0;
        font-size: 18px;
        background: #ddd;
        border: none;
        cursor: pointer;
    }
    .error {
        margin-top: 10px;
        color: red;
        font-size: 16px;
    }
    .register-link {
        position: fixed;
        bottom: 20px;
        right: 30px;
        background: #ddd;
        padding: 10px 20px;
        font-size: 16px;
        text-decoration: none;
        color: black;
    }
</style>
</head>
<body>

    <h2>ログインID</h2>

    <!-- ログインフォーム -->
    <form action="<%= request.getContextPath() %>/LoginServlet" method="post">

        <input type="text" name="loginId" class="input-box">

        <h2>パスワード</h2>
        <input type="password" name="password" class="input-box">

        <br>
        <button type="submit" class="btn">ログイン</button>

        <!-- エラーメッセージ表示欄 -->
        <div class="error">
            ${error}
        </div>
    </form>

    <!-- ユーザー登録画面へ遷移 -->
    <a href="<%= request.getContextPath() %>/AccountRegisterServlet" class="register-link">
        ユーザー登録はこちら
    </a>

</body>
</html>
