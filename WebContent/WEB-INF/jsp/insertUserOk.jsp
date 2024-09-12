<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
// リクエストスコープに保存されたエラーメッセージを取得
String errorMsg = (String)request.getAttribute("errorMsg");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レビューズジェム掲示板</title>
</head>
<body>

<% if (errorMsg != null) { %>
 <p style="color: red;"><%= errorMsg %></p>
 <a href="index.jsp">TOPへ</a>
<% } else { %>
<p>ユーザー登録が成功しました</p>
 <a href="index.jsp">TOPへ</a>
<% } %>

<jsp:include page="footer.jsp" />

</body>
</html>