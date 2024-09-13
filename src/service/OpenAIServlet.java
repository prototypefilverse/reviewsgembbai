package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@WebServlet("/OpenAIServlet")
public class OpenAIServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
 // OpenAI APIキー
    private static final String API_KEY = System.getenv("OPENAI_API_KEY");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 投稿内容を取得
        String userInput = request.getParameter("text");

        // OpenAI APIとの通信処理
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", new JSONArray().put(new JSONObject()
                .put("role", "user")
                .put("content", userInput)
            )).toString());
        Request apiRequest = new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .post(body)
            .addHeader("Authorization", "Bearer " + API_KEY)
            .addHeader("Content-Type", "application/json")
            .build();

        try (Response apiResponse = client.newCall(apiRequest).execute()) {
            if (apiResponse.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(apiResponse.body().string());
                String aiResponse = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

                // セッションスコープにAIの応答を保存
                HttpSession session = request.getSession();
                session.setAttribute("aiResponse", aiResponse);

                // メインページにリダイレクト
                response.sendRedirect("Main");
            } else {
                // エラー処理
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "APIエラーが発生しました");
            }
        }
    }
}




