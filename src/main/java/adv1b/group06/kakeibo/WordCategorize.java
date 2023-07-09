package adv1b.group06.kakeibo;

import adv1b.group06.kakeibo.model.Category;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品名をOpenAIのAPIを利用してカテゴライズするクラス
 *
 * @author 須藤
 */
public class WordCategorize {
    private static String key = "";

    /**
     * OpenAIのAPI keyのsetter
     *
     * @param key API key
     */
    public static void setKey(String key) {
        WordCategorize.key = key;
    }

    /**
     * 商品名をAPIに問い合わせてカテゴライズする
     *
     * @param item 商品名
     * @return カテゴリ
     */
    public static Category fetchCategory(String item) {
        if (key == null || key.equals("")) {
            System.out.println("No API key");
            return Category.getUnassignedCategory();
        }
        try {
            // APIエンドポイントのURLを指定
            URL url = new URI("https://api.openai.com/v1/chat/completions").toURL();

            // HttpURLConnectionを作成
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + key);
            connection.setDoOutput(true);

            // リクエストボディを指定
            List<String> categories = new ArrayList<>();
            for (Category c : Category.getCategoriesList()) {
                categories.add(c.toString());
            }
            String content = "'%s'は%s?1単語で".formatted(item, String.join(",", categories));
            String requestBody = "{\"model\": \"gpt-3.5-turbo\",\"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}".formatted(content);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();

            // レスポンスを取得
            Gson gson = new Gson();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            CategorizeAPIResponse res = gson.fromJson(response.toString(), CategorizeAPIResponse.class);


            // 結果を表示
            String resCategory = res.getChoices()[0].getMessage().getContent();

            // 接続を閉じる
            connection.disconnect();
            for (Category c : Category.getCategoriesList()) {
                if (resCategory.contains(c.toString())) {
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class CategorizeAPIResponse {
        private Choice[] choices;

        public Choice[] getChoices() {
            return choices;
        }

        public static class Choice {
            private Message message;

            public Message getMessage() {
                return message;
            }
        }

        public static class Message {
            private String content;

            public String getContent() {
                return content;
            }
        }

    }

}
