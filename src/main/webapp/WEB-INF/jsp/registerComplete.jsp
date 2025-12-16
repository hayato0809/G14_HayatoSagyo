<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録完了</title>

<style>
    body {
        font-family: sans-serif;
        text-align: center;
        margin-top: 100px;
    }

    .btn {
        width: 200px;
        height: 40px;
        margin-top: 40px;
        font-size: 18px;
        background: #ddd;
        border: none;
        cursor: pointer;
    }
</style>

</head>
<body>

    <h1>アカウント登録完了</h1>

    <p>ユーザー登録が完了しました。</p>

    <!-- ログイン画面へ戻るボタン -->
    <button class="btn"
        onclick="location.href='<%= request.getContextPath() %>/Login'">
        ログイン画面へ戻る
    </button>

</body>
</html>
