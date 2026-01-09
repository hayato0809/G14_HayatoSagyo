<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品追加</title>
</head>
<body>

<h2>商品追加</h2>

<form method="post">

  カテゴリー：
  <select name="categoryId">
    <c:forEach var="c" items="${categories}">
      <option value="${c.categoryId}">${c.categoryName}</option>
    </c:forEach>
  </select>
  <br><br>

  商品名：<br>
  <textarea name="itemName" rows="2" cols="30"></textarea><br><br>

  値段：<br>
  <input type="number" name="priceYen"> 円<br><br>

  オプション（おすすめトッピング）：<br>
  <c:forEach var="t" items="${toppings}">
    <label>
      <input type="checkbox" name="toppingIds" value="${t.toppingId}">
      ${t.toppingName}（+${t.priceYen}円）
    </label><br>
  </c:forEach>

  <br>
  <button type="submit">追加</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/admin/menu/list">← 一覧へ</a>

</body>
</html>
