package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsersDAO;
import model.User;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");

		// DAOのインスタンスを作り
		UsersDAO loginUser = new UsersDAO();
		// boolean型変数にメソッドの結果を格納
		boolean isValidUser = loginUser.isUserValid(name, pass);

		User user = new User(name,pass);

		if(isValidUser == true){
		   // true だった場合のみセッションスコープに保存
	        HttpSession session = request.getSession();
           session.setAttribute("loginUser", user);
	    	}

		// フォワード先は同じ 向こうでの処理はセッションスコープに保存されているかで変わる
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/loginResult.jsp");
		dispatcher.forward(request, response);

	}
}
