package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CheckAPIKey")
public class OpenAIServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 環境変数からAPIキーを取得
        String apiKey = System.getenv("OPENAI_API_KEY");

        // APIキーが設定されているか確認
        if (apiKey != null && !apiKey.isEmpty()) {
            response.getWriter().println("APIキーが正しく設定されています。");
            response.getWriter().println("APIキー: " + apiKey);
        } else {
            response.getWriter().println("APIキーが設定されていません。");
        }
    }
}





