package net.factory.net;

import com.google.gson.Gson;

import net.common.Common;
import net.factory.main.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CLW on 2017/8/29.
 */

public class NetWork {

    //获得一个retrofit
    public static Retrofit getRetrofit()
    {
        //得到一个okhttpclient
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder();
        //设置网络连接地址
       Retrofit retrofit = mRetrofitBuilder.baseUrl(Common.Constance.API_URL)
               //设置client
                .client(client)
               //设置解析器
               .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return  retrofit;
    }

}
