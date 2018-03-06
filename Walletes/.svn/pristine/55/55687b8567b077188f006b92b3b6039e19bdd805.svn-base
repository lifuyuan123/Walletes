package com.wallet.bo.wallets.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wallet.bo.wallets.Utils.Utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppClient {
    public static Retrofit mRetrofit;

    public static synchronized Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //  builder.addInterceptor(new LogInterceptor());

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.i("RetrofitLog", "retrofitBack = " + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);

            builder.addInterceptor(new ReceivedCookiesInterceptor(Utils.getContext()));
            builder.addInterceptor(new AddCookiesInterceptor(Utils.getContext(), ""));

//            // 添加公共参数拦截器
//            HttpCommonInterceptor basicParamsInterceptor = new HttpCommonInterceptor.Builder()
//                    .addHeaderParams(RequestHelpr.APPIDKEY, RequestHelpr.APPID)
//                    .build();
//            builder.addInterceptor(basicParamsInterceptor);


            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }
        return mRetrofit;
    }

}
