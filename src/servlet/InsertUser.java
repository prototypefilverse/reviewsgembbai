package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsersDAO;
import model.User;

@WebServlet("/InsertUser")
public class InsertUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    RequestDispatcher dispatchar = request.getRequestDispatcher("WEB-INF/jsp/insertUserForm.jsp");
	    dispatchar.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		// ユーザーインスタンスの生成
		User user =  new User(name, pass);

		if (name != null && name.length() != 0 && pass != null && pass.length() != 0){

		// DAOのインスタンスを作りdaoのinsertUserメソッドを使う
		UsersDAO dao = new UsersDAO();
        dao.insertUser(user);

		} else {

		   // エラーメッセージをリクエストスコープに保存 （名前, インスタンス）
		    request.setAttribute("errorMsg", "登録できませんでした。いずれかの入力が空です");

		  }

	     // 登録完了画面にフォワード
		 RequestDispatcher dispatchar = request.getRequestDispatcher("WEB-INF/jsp/insertUserOk.jsp");
		 dispatchar.forward(request,response);
     }

	}
