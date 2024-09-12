<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>

<%
// セッションスコープからユーザー情報を取得
 User loginUser = (User)session.getAttribute("loginUser");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レビューズジェム掲示板</title>
<link rel="stylesheet" type="text/css" href="styles/styles.css">
</head>
<body>

<div class="container">
  <% if(loginUser != null) { %>
    <p class="success-msg">レビューズジェム掲示板ログインに成功しました</p>
    <p>ようこそ<%= loginUser.getName() %> さん</p>
    <a href="Main" class="link">つぶやき投稿・閲覧へ</a>
  <% } else { %>
    <p class="error-msg">ログインに失敗しました</p>
    <a href="index.jsp" class="link">TOPへ</a>
  <% } %>
</div>

<jsp:include page="footer.jsp" />

</body>
</html>
