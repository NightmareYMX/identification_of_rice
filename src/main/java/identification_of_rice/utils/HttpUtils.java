package identification_of_rice.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;

public class HttpUtils {
    public static String getResult(String fileName) {
        String predictUrl = "http://127.0.0.1/predict";//本地服务器看flask的ip和端口号
        OkHttpClient client = new OkHttpClient();
        String url = predictUrl + "?fileName=" + fileName;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<RiceResultUtils>(){}.getType();
        try {
            response = client
                    .newCall(request)
                    .execute();
            if (response.code() == 200) {
                String result = response.body().string();
                RiceResultUtils riceResult = gson.fromJson(result, type);
                return RiceResultUtils.convertData(riceResult.getResult());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.body().close();
            }
        }
        return null;
    }
}
