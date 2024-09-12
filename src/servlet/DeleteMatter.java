package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MuttersDAO;

@WebServlet("/Delete")
public class DeleteMatter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		 // 削除ボタンが押された時の文字列"true"を受け取る
		 String confirmed = request.getParameter("confirmed");

		// confirmedがnullでなく文字列"true"であるか確認している
		if (confirmed != null && confirmed.equals("true")){
		// そうであればDAOのインスタンスを作りdaoのdeleteメソッドを使う
		MuttersDAO dao = new MuttersDAO();
		dao.delete(id);
		}
		
		// 同じ画面にリダイレクト
		response.sendRedirect("Main");

	}

}
