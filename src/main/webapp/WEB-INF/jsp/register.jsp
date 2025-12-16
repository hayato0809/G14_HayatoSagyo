<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー登録</title>
</head>
<body>

<h2>ユーザー登録</h2>

<form action="${pageContext.request.contextPath}/AccountRegister" method="post">
    <div>
        ID：
        <input type="text" name="loginId">
    </div>
    <br>
    <div>
        パスワード：
        <input type="password" name="password">
    </div>
    <br>
    <button type="submit">登録</button>
</form>

<p style="color:red;">
    ${error}
</p>

<br>
<a href="${pageContext.request.contextPath}/Login">ログイン画面へ戻る</a>

</body>
</html>
