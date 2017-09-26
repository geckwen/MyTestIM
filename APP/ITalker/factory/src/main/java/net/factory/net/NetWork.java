package net.factory.net;

import android.text.TextUtils;

import net.common.Common;
import net.factory.present.Factory;
import net.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CLW on 2017/8/29.
 */

public class NetWork {
    private static final NetWork  singeInstance ;

    private static volatile Retrofit retrofit;
    private NetWork()
    {
        retrofit = getRetrofit();
    }
    static {
        singeInstance = new NetWork();
    }

    //获得一个retrofit
    private static Retrofit getRetrofit()
    {
        if(singeInstance.retrofit!=null)
            return retrofit;

        //得到一个okhttpclient
        OkHttpClient client = new OkHttpClient.Builder()
                //给所有请求设置一个拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拿到我们的请求
                        Request cilent = chain.request();
                        //重新进行build
                        Request.Builder builder =cilent.newBuilder();
                        if(!TextUtils.isEmpty(Account.getToken()))
                        {
                            //注入一个token
                            builder.addHeader("token",Account.getToken());
                        }
                            Request newRequest = builder.build();
                        //将request返回response
                        return chain.proceed(newRequest);
                    }
                })
                .build();
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

    /**
     * 获取Remote
     * @return 返回一个RemoteService
     */
    public static RemoteService getAccountRemoteService()
    {
        return getRetrofit().create(RemoteService.class);
    }

}
