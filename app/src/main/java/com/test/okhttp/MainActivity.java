package com.test.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.okhttp.listener.DisposeDataHandle;
import com.test.okhttp.listener.DisposeDataListener;
import com.test.okhttp.request.CommonRequest;
import com.test.okhttp.response.CommonJsonCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                sendRequest();
                break;
            case R.id.button2:
                fengzhuangTest();
                break;
        }
    }

    private void fengzhuangTest() {
        CommentOkHttpClient.sendRequest(CommonRequest.createGetRequest("http://api.map.baidu.com/telematics/v3/weather?location=嘉兴&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ",null),
                new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        Toast.makeText(MainActivity.this,"成功了",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Toast.makeText(MainActivity.this,"失败了",Toast.LENGTH_SHORT).show();

                    }
                })));
    }

    private void sendRequest() {
        //创建一个OkHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();

        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"失败了",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //请求成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"成功了",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

}
