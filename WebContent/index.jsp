<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>レビューズジェム掲示板</title>
    <link rel="stylesheet" type="text/css" href="styles/styles.css">
</head>
<body>
<div class="container">
    <h1>レビューズジェム掲示板へようこそ</h1>
    <form action="Login" method="post" class="form-box">
        <div class="input-group">
            <label for="username">ユーザー名:</label>
            <input type="text" id="username" name="name" required>
        </div>
        <div class="input-group">
            <label for="password">パスワード:</label>
            <input type="password" id="password" name="pass" required>
        </div>
        <input type="submit" value="ログイン" class="btn submit-btn">
    </form>
    <div class="link-group">
        <a href="InsertUser" class="link">ユーザー登録へ</a>
        <a href="Main" class="link">ログインせずに掲示板を閲覧する</a>
    </div>
</div>

<jsp:include page="WEB-INF/jsp/footer.jsp" />
</body>
</html>












