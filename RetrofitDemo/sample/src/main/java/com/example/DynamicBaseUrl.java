package com.example;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * 作者： hiram on 2017/3/24
 * 动态改变BaseURL
 * 实现拦截器
 */

public class DynamicBaseUrl {
    public interface Pop {
        @GET("robots.txt")
        Call<ResponseBody> robots();
    }
    /*
    * 使用拦截器动态改变baseUrl
    * */
    static final class HostSelectionInterceptor implements Interceptor{
        private volatile String host;
        public void setHost(String host) {
            this.host = host;
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String host = this.host;
            if (host != null) {
                HttpUrl newUrl = request.url().newBuilder()
                        .host(host)
                        .build();
                request = request.newBuilder()
                        .url(newUrl)
                        .build();
            }
            return chain.proceed(request);
        }
    }
    public static void main(String... args) throws IOException {
        HostSelectionInterceptor hostSelectionInterceptor
                = new HostSelectionInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(hostSelectionInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.coca-cola.com/")
                .callFactory(okHttpClient)
                .build();
        Pop pop = retrofit.create(Pop.class);
        hostSelectionInterceptor.setHost("www.pepsi.com");
//        Call<ResponseBody> ddd = pop.robots();
        Response<ResponseBody> response1 = pop.robots().execute();
        System.out.println("Response from1: " + response1.raw().request().url());
        System.out.println(response1.body().string());
//        hostSelectionInterceptor.setHost("www.pepsi.com");
//        Response<ResponseBody> response2 = pop.robots().execute();
//        System.out.println("Response from2: " + response2.raw().request().url());
//        System.out.println(response2.body().string());

    }
}
