<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップ画面</title>

<style>
    body {
        font-family: sans-serif;
        text-align: center;
        margin-top: 60px;
    }
    .btn {
        display: block;
        width: 260px;
        height: 45px;
        margin: 20px auto;
        font-size: 18px;
        background: #ddd;
        border: none;
        cursor: pointer;
    }
    .logout {
        position: fixed;
        top: 20px;
        right: 30px;
        background: #ddd;
        padding: 10px 20px;
        border: none;
        cursor: pointer;
        font-size: 16px;
    }
</style>

</head>
<body>

<h2>トップ画面</h2>

<!-- 各画面への遷移 -->
<form action="${pageContext.request.contextPath}/DailySales" method="get">
    <button class="btn">日別売り上げ確認</button>
</form>

<form action="${pageContext.request.contextPath}/ProductSales" method="get">
    <button class="btn">商品別売り上げ確認</button>
</form>

<form action="${pageContext.request.contextPath}/ProductEdit" method="get">
    <button class="btn">商品編集</button>
</form>

<!-- ログアウト -->
<form action="${pageContext.request.contextPath}/Top" method="post">
    <button class="logout">ログアウト</button>
</form>

</body>
</html>
