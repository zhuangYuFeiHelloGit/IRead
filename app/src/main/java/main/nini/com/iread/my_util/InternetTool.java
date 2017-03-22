package main.nini.com.iread.my_util;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zyf on 2017/2/12.
 */

public class InternetTool {
    private OkHttpClient okHttpClient;
    private static InternetTool tool;
    private static final String TAG = "InternetTool";

    private InternetTool() {
        okHttpClient = new OkHttpClient();
    }

    public static InternetTool getInstance() {
        if (tool == null) {
            tool = new InternetTool();
        }
        return tool;
    }

    public void getRequest(final String url, final Class clazz, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request =
                        new Request.Builder()
                                .url(url).build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                Object o = null;
                try {
                    response = call.execute();
                    Gson gson = new Gson();
                    o = gson.fromJson(response.body().string(),clazz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.obj = o;
                handler.sendMessage(message);
            }
        }).start();

    }

    public void getRequest(final String url, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request =
                        new Request.Builder()
                                .url(url).build();
                Call call = okHttpClient.newCall(request);
                Response response = null;
                Object o = null;
                try {
                    response = call.execute();
                    String result = response.body().string();
                    JSONArray array = new JSONArray(result);
                        JSONObject objects = array.getJSONObject(0);
                        String id = objects.getString("_id");
                        Message message = handler.obtainMessage();
                        message.obj = id;
                        handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
