package com.test.okhttp;


import com.test.okhttp.https.HttpsUtils;
import com.test.okhttp.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 请求的发送，请求参数的配置，https的支持
 * Created by lady_zhou on 2017/9/8.
 */

public class CommentOkHttpClient {
    private static final int TIME_OUT = 30;//超时参数
    private static OkHttpClient mOkHttpClient;

    //为Client配置参数
    static {
        //创建我们Client对象的构建者
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        //为构建者填充连接超时时间
        okhttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        //为构建者填充读超时时间
        okhttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        //为构建者填充写超时时间
        okhttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        okhttpBuilder.followRedirects(true);
        //https支持
        okhttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

//        okhttpBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        okhttpBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());

        //生成我们的Client对象
        mOkHttpClient = okhttpBuilder.build();
    }

    /**
     * 发送具体的http/https请求
     *
     * @param request
     * @param commCallback
     * @return
     */

    public static Call sendRequest(Request request, CommonJsonCallback commCallback) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(commCallback);
        return call;
    }
}
