<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>エラー</title>
  <style>
    body { font-family: sans-serif; }
    .wrap { width: 760px; margin: 60px auto; }
    .box { border: 1px solid #ffb3b3; background:#ffe7e7; padding: 18px; border-radius: 10px; }
    a { display:inline-block; margin-top: 14px; }
  </style>
</head>
<body>
<div class="wrap">
  <div class="box">
    <div><c:out value="${error}" /></div>
    <a href="${pageContext.request.contextPath}/kitchen">戻る</a>
  </div>
</div>
</body>
</html>
