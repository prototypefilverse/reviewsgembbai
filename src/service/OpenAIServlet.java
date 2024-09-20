package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            	
            	String responseBody = apiResponse.body().string();
            	JSONObject json = new JSONObject(responseBody);
                String aiResponse = json.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

                // AIの応答を返す
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write(aiResponse);

            } else {
                // エラー処理
            	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            	response.getWriter().write("AIからの応答に失敗しました。");
            }
        }
    }
}




