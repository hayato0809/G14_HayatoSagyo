<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>商品一覧</title>
  <style>
    body { font-family: sans-serif; }
    .topbar { display:flex; align-items:center; justify-content:space-between; margin: 10px 0; }
    .btn { padding: 6px 12px; border: 1px solid #999; background: #eee; cursor: pointer; border-radius: 6px; }
    table { width: 100%; border-collapse: collapse; }
    th, td { border-bottom: 1px solid #ddd; padding: 8px; text-align: left; }
    .right { text-align:right; }
  </style>
</head>
<body>

<div class="topbar">
  <div>
    <a class="btn" href="${pageContext.request.contextPath}/Top">ホーム</a>

    <form method="get" action="${pageContext.request.contextPath}/admin/menu/list" style="display:inline;">
      <select name="categoryId" onchange="this.form.submit()">
        <option value="0" <c:if test="${selectedCategoryId == 0}">selected</c:if>>全て</option>
        <c:forEach var="c" items="${categories}">
          <option value="${c.categoryId}" <c:if test="${selectedCategoryId == c.categoryId}">selected</c:if>>
            ${c.categoryName}
          </option>
        </c:forEach>
      </select>
    </form>
  </div>

  <div>
    <a class="btn" href="${pageContext.request.contextPath}/admin/menu/add">追加</a>
  </div>
</div>

<h2>商品一覧</h2>

<table>
  <thead>
    <tr>
      <th>商品名</th>
      <th>カテゴリー</th>
      <th class="right">値段</th>
      <th>表示設定</th>
      <th>編集</th>
    </tr>
  </thead>

  <tbody>
    <c:forEach var="it" items="${items}">
      <tr>
        <td>${it.itemName}</td>
        <td>${it.categoryName}</td>
        <td class="right">${it.priceYen}円</td>

        <td>
          <form method="post" action="${pageContext.request.contextPath}/admin/menu/toggleVisible" style="display:inline;">
            <input type="hidden" name="itemId" value="${it.itemId}">
            <input type="hidden" name="categoryId" value="${selectedCategoryId}">
            <button class="btn" type="submit">
              <c:choose>
                <c:when test="${it.visible}">非表示</c:when>
                <c:otherwise>表示</c:otherwise>
              </c:choose>
            </button>
          </form>
        </td>

        <td>
          <a class="btn" href="${pageContext.request.contextPath}/admin/menu/edit?itemId=${it.itemId}">&gt;</a>
        </td>
      </tr>
    </c:forEach>

    <c:if test="${empty items}">
      <tr><td colspan="5">商品がありません</td></tr>
    </c:if>
  </tbody>
</table>

</body>
</html>
