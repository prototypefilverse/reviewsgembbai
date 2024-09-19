<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User, model.Mutter, java.util.List" %>

<%
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
sdf.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Tokyo"));

User loginUser = (User)session.getAttribute("loginUser");
List<Mutter> mutterList = (List<Mutter>)request.getAttribute("mutterList");
String errorMsg = (String)request.getAttribute("errorMsg");
String aiResponse = (String)session.getAttribute("aiResponse"); 

int currentPage = (int) request.getAttribute("currentPage");
int totalPages = (int) request.getAttribute("totalPages");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>レビューズジェム掲示板</title>
<link rel="stylesheet" type="text/css" href="styles/styles.css">
</head>
<body>

<div class="container">
  <h1 class="center-text">レビューズジェム掲示板</h1>

 
  <div class="header-info">
    <div class="user-info">
      <% if (loginUser != null) { %>
        <div class="login-status">
          <span><%= loginUser.getName() %>さん、ログイン中</span>
          <a href="Logout" class="link">ログアウト</a>
        </div>
      <% } else { %>
        <p>※投稿するにはログインが必要です。
        <a href="index.jsp" class="link">ログインページへ</a>
      </p>
      <% } %>
    </div>
    <a href="Main" class="link update-link">更新</a>
  </div>


<div class="content-wrapper">

 <% if (loginUser != null) { %>
 <div class="form-section">
  <!-- 掲示板投稿部分 -->
  <div class="board-section">
    <form action="Main" method="post" class="form-box">
      <p><strong>【掲示板に投稿する】</strong></p>
      <textarea id="postContent" name="text" rows="4" maxlength="200" required></textarea>
      <div id="charCount" class="char-count">0/200</div>
      <input type="submit" value="投稿" class="btn board-submit-btn">
    </form>
  </div>
   <div class="ai-section">
    <form action="OpenAIServlet" method="post" class="form-box">
      <p><strong>【AIに何か尋ねる】</strong></p>
      <textarea id="aiQuestionContent" name="text" rows="4" required></textarea>
      <input type="submit" value="質問" class="btn ai-submit-btn">
    </form>
     <!-- AIに質問する部分 -->
    <% if (aiResponse != null) { %>
      <div class="ai-response-box">
        <p>AIからの応答:<%= aiResponse %></p>
      </div>
      <% session.removeAttribute("aiResponse"); %>
    <% } %>
    </div>
  </div>
<% } %>

  <% if (errorMsg != null) { %>
    <p class="error-msg"><%= errorMsg %></p>
  <% } %>

  <%-- for文 --%>
  <div class="mutter-list">
   <% for (int i = 0; i < mutterList.size(); i++) { %>
   <div class="mutter-item">
    <span class="mutter-dete">投稿日時：<%= sdf.format(mutterList.get(i).getPostDate()) %></span>
    <span class="mutter-user">　<%= mutterList.get(i).getUserName() %></span>
    <p class="mutter-text"><%= mutterList.get(i).getText() %></p>

      <%-- ログインしていない場合は表示せずNullPointerExceptionを防ぐ --%>
      <% if (loginUser != null && mutterList.get(i).getUserName().equals(loginUser.getName())) { %>
        <%-- onsubmit属性により、ボタンを押すと下に用意してる confirmDelete() がreturnで発火する --%>
        <form action="Delete" method="post" onsubmit="return confirmDelete();" class="delete-form">
          <%-- hiddenでつぶやきのidと、識別名confirmedの"true"文字列を送る --%>
          <input type="hidden" name="id" value="<%= mutterList.get(i).getId() %>">
          <input type="hidden" name="confirmed" value="true">
          <input type="submit" value="削除" class="btn btn-delete">
        </form>
      <% } %>
    </div>
  <% } %>
  </div> 
  
  <br>
    <div class="pagination">
    <% if (currentPage > 1) { %>
      <a href="Main?pageNumber=<%= currentPage - 1 %>" class="link">前へ</a>
    <% } %>

    <% for (int i = 1; i <= totalPages; i++) { %>
      <a href="Main?pageNumber=<%= i %>" class="link <%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
    <% } %>

    <% if (currentPage < totalPages) { %>
      <a href="Main?pageNumber=<%= currentPage + 1 %>" class="link">次へ</a>
    <% } %>
  </div>
</div>

</div>

<script>

document.getElementById('postContent').addEventListener('input', function() {
    var charCount = this.value.length;
    var charCountElem = document.getElementById('charCount');
    charCountElem.textContent = charCount + "/200";
    charCountElem.style.color = charCount > 180 ? 'red' : '#333';
});

function confirmDelete() {
    return confirm("本当にこのつぶやきを削除しますか？");
}

</script>

<jsp:include page="footer.jsp" />

</body>
</html>





