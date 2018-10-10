package com.nanshan.support.http;

import com.google.gson.Gson;
import com.nanshan.support.utils.LogUtils;

import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiangbo on 2018/9/13.
 * http基础类
 */

public class HttpUtils {
    //储存Retrofit的集合，key为baseUrl，value为Retrofit对象
    private WeakHashMap<String, Retrofit> mHttps = new WeakHashMap<>();
    private OkHttpClient.Builder okHttpClient;

    //超时时间，单位s
    private static final int TIME_OUT = 20;

    private static class HttpImpl {
        private static final HttpUtils mInstance = new HttpUtils();
    }

    public static HttpUtils getInstance() {
        return HttpImpl.mInstance;
    }

    private HttpUtils() {
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.i("http message:" + message);
            }
        });
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //自定义OkHttpClient
        okHttpClient = new OkHttpClient.Builder();
        //添加拦截器
        okHttpClient.addInterceptor(httpLoggingInterceptor);
    }

    public Retrofit create(String baseUrl) {
        Retrofit retrofit = mHttps.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //可以接收自定义的Gson，当然也可以不传
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient.build())
                    .build();
            mHttps.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    public Retrofit create(String baseUrl, Gson gson) {
        Retrofit retrofit = mHttps.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //可以接收自定义的Gson，当然也可以不传
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient.build())
                    .build();
            mHttps.put(baseUrl, retrofit);
        }
        return retrofit;
    }
}
