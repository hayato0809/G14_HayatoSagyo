<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品編集</title>
<style>
  body { font-family: sans-serif; }
  .btn { padding: 6px 12px; border: 1px solid #999; background: #eee; cursor: pointer; border-radius: 6px; }
  .box { max-width: 700px; }
  .row { margin: 10px 0; }
  .err { color: red; margin: 10px 0; }
</style>
</head>
<body>

<a class="btn" href="${pageContext.request.contextPath}/admin/menu/list">← 一覧へ</a>

<h2>商品編集</h2>

<c:if test="${not empty error}">
  <div class="err">${error}</div>
</c:if>

<div class="box">
  <form method="post" action="${pageContext.request.contextPath}/admin/menu/edit">
    <input type="hidden" name="itemId" value="${item.itemId}">

    <div class="row">
      カテゴリー：
      <select name="categoryId">
        <c:forEach var="c" items="${categories}">
          <option value="${c.categoryId}"
            <c:if test="${c.categoryId == item.categoryId}">selected</c:if>>
            ${c.categoryName}
          </option>
        </c:forEach>
      </select>
    </div>

    <div class="row">
      商品名：<br>
      <textarea name="itemName" rows="2" cols="40">${item.itemName}</textarea>
    </div>

    <div class="row">
      値段：<br>
      <input type="number" name="priceYen" value="${item.priceYen}"> 円
    </div>

    <div class="row">
      オプション（おすすめトッピング）：<br>
      <c:forEach var="t" items="${toppings}">
        <label>
          <input type="checkbox" name="toppingIds" value="${t.toppingId}"
            <c:if test="${selectedToppingIds.contains(t.toppingId)}">checked</c:if>>
          ${t.toppingName}（+${t.priceYen}円）
        </label><br>
      </c:forEach>
    </div>

    <div class="row">
      <button class="btn" type="submit">保存</button>
    </div>
  </form>
</div>

</body>
</html>
