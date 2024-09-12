package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GetMutterListLogic;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }

        int pageSize = 10;

		// つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = getMutterListLogic.execute(pageNumber, pageSize);
        int totalMutters = getMutterListLogic.getTotalMutters(); // 総つぶやき数を取得
        int totalPages = (int) Math.ceil((double) totalMutters / pageSize);

        request.setAttribute("mutterList", mutterList);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);

		// ログインしているか確認するため
		// セッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");

	    // 現状、ログインの有無にかかわらずmain.jspを表示できるように改修中
		if (loginUser == null) {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		    dispatcher.forward(request, response);
		} else {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		    dispatcher.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("text");

		// 入力値チェック
		if (text != null && text.length() != 0){

	      // セッションスコープに保存されたユーザー情報を取得
		  HttpSession session = request.getSession();
		  User loginUser = (User)session.getAttribute("loginUser");

		  // つぶやきをつぶやきリストに追加
		  Mutter mutter = new Mutter(loginUser.getName(),text);
		  PostMutterLogic postMutterLogic = new PostMutterLogic();
		  postMutterLogic.execute(mutter);

		} else {

		  // エラーメッセージをリクエストスコープに保存 （名前, インスタンス）
			request.setAttribute("errorMsg", "つぶやきが入力されていません");

		}

		doGet(request, response);
	}

}

