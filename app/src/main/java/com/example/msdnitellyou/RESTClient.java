package com.example.msdnitellyou;

import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;

import com.example.msdnitellyou.model.KeyValModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 * 网络访问类
 */
public class RESTClient {
    public static final String SERVER_HOST = "http://msdn.itellyou.cn/";
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Gson jsonConverter = new Gson();
    private static final String TAG = "NetworkTest";
    /**
     * 发送POST请求，并返回指定的数据模型
     *  @param apiPath api路径
     * @param <T> 返回的数据类型
     * @param <R> 请求的数据类型
     * @param Data 请求的Post数据，将被转换成Json
     * @return 返回一个类型为T的对象
     * @throws ServerException 抛出服务器异常
     */
    public static <T,R> T postAsync(String apiPath,R Data,Class<T> ClassOfT) throws ServerException {
        //将对象模型转换成Json
        String dataJson = jsonConverter.toJson(Data);

        //准备HTTP POST 内容正文
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody postBody = RequestBody.create(mediaType,dataJson);

        //封装请求
        Request request =  packupHeader(SERVER_HOST + apiPath)
                .post(postBody)
                .build();

        try{
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                return jsonConverter.fromJson(response.body().string(),ClassOfT);
            }else {
                throw new ServerException(-3000,"天呐，服务不可用.");
            }
        }catch (Exception ex){
            throw new ServerException(-5000,"天呐，网络不可用.");
        }
    }

    /**
     * 发送Get请求，并返回指定的数据类型
     * @param apiPath api路径
     * @param <T> 返回的数据类型
     * @return 返回一个类型为T的对象
     * @throws ServerException 抛出服务器异常
     */
    public static <T> T getAsync(String apiPath,Class<T> ClassOfT) throws ServerException{

        Request request = packupHeader(SERVER_HOST + apiPath)
                .get()
                .build();

        try{
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                return jsonConverter.fromJson(response.body().string(),ClassOfT);
            }else {
                throw new ServerException(-3000,"天呐，服务不可用.");
            }
        }catch (Exception ex){
            throw new ServerException(-5000,"天呐，网络不可用.");
        }
    }

    public static void getAsync(String apiPath, final HttpCallback callback) throws ServerException{
        Request request = packupHeader(SERVER_HOST + apiPath)
                .get()
                .build();

        try{
            Log.d(TAG, "getAsync: OK");
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e){

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    callback.onSuccess(response.body().string());
                }
            });
        }catch (Exception ex){
            throw new ServerException(-5000,"天呐，网络不可用");
        }
    }

    public static <T> List<T> postAsync(String apiPath, Class<T> tClass, KeyValModel... pram) throws ServerException{
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        for (KeyValModel kvs : pram){
            formEncodingBuilder.add(kvs.getKey(),kvs.getVal());
        }
        RequestBody requestBody = formEncodingBuilder.build();
        Request request =  packupHeader(SERVER_HOST + apiPath)
                .post(requestBody)
                .build();
        try{
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                Log.d(TAG, "postAsync: Ok");
                JsonParser jsonParsert =new JsonParser();
                String json = response.body().string();

//                JsonElement je = ;
                JsonArray jsonArray = jsonParsert.parse(json).getAsJsonArray();
                Log.d(TAG, "postAsync: jsonArray");
                List<T> array = new ArrayList<>();
                for (JsonElement element : jsonArray){
                    array.add(jsonConverter.fromJson(element,tClass));
                }
                return array;
            }else {
                Log.d(TAG, "postAsync: -3000");
                throw new ServerException(-3000,"天呐，服务不可用.");
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Log.d(TAG, "postAsync: " + ex.getMessage());
            Log.d(TAG, "postAsync: -5000");
            throw new ServerException(-5000,"天呐，网络不可用.");
        }
    }

    public static <T> void postAsync(String apiPath,final Class<T> tClass, final IHttpCallback callback, KeyValModel... pram){
        FormEncodingBuilder formData = new FormEncodingBuilder();
        for (KeyValModel lvs: pram){
            formData.add(lvs.getKey(),lvs.getVal());
        }

        RequestBody requestBody = formData.build();
        Request request = packupHeader(SERVER_HOST + apiPath)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                callback.onSuccess(jsonConverter.fromJson(json,tClass));
            }
        });
    }

    /**
     * 打包HTTP报头和签名
     * @param url  请求地址
     * @return 请求构建对象
     */
    private static Request.Builder packupHeader(String url){

        Request.Builder request = new Request.Builder()
                .url(url)
                .addHeader("Referer","http://msdn.itellyou.cn/")
                .addHeader("accept", "application/json; charset=UTF-8");
        return  request;
    }
}
