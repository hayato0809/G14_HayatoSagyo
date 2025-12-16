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
        margin-top: 90px;
    }
    .input-box {
        width: 520px;
        height: 34px;
        background: #ddd;
        border: none;
        padding: 6px 10px;
        font-size: 16px;
        box-sizing: border-box;
    }
    .label {
        font-size: 26px;
        margin: 26px 0 16px 0;
    }
    .btn {
        width: 160px;
        height: 42px;
        margin-top: 26px;
        font-size: 18px;
        background: #ddd;
        border: none;
        cursor: pointer;
    }
    .error-box {
        width: 360px;
        margin: 18px auto 0 auto;
        padding: 10px 0;
        background: #ddd;
        font-size: 18px;
        color: #000;
        min-height: 24px; /* 何もなくても高さ確保 */
    }
    .register-link {
        position: fixed;
        right: 40px;
        bottom: 26px;
        width: 240px;
        padding: 12px 10px;
        background: #ddd;
        text-decoration: none;
        color: #000;
        font-size: 16px;
        display: inline-block;
        text-align: center;
    }
</style>
</head>
<body>

    <div class="label">ログインID</div>
    <form action="<%= request.getContextPath() %>/Login" method="post">
        <input type="text" name="loginId" class="input-box"
               value="${param.loginId != null ? param.loginId : ''}">

        <div class="label">パスワード</div>
        <input type="password" name="password" class="input-box">

        <br>
        <button type="submit" class="btn">ログイン</button>

        <div class="error-box">
            ${error}
        </div>
    </form>

    <a class="register-link" href="<%= request.getContextPath() %>/AccountRegister">
        ユーザー登録はこちら
    </a>

</body>
</html>
