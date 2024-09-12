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
        <h2>ユーザー登録</h2>
        <form action="InsertUser" method="post">
            <div class="input-group">
                <label for="name">ユーザー名:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="input-group">
                <label for="pass">パスワード:</label>
                <input type="password" id="pass" name="pass" required>
            </div>
            <input type="submit" value="登録" class="btn">
        </form>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
