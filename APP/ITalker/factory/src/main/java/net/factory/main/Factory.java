package net.factory.main;

import android.support.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.common.app.Application;
import net.common.factory.data.DataSource;
import net.factory.R;
import net.factory.model.base.RspModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 单例模式
 * Created by CLW on 2017/8/20.
 */

public class Factory {
    //线程数量
    private static final  int THREAD_COUNT = 4;
    //全局的线程池
    private final Executor executor;
    public final Gson gson;
    //单例模式的构造模式
    private static  final  Factory factory = new Factory();
    private Factory(){
        //创建一个线程池为4个.
        executor= Executors.newFixedThreadPool(4);
        gson = new GsonBuilder()
                //设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();
    }

    public static Factory getInstance()
    {
        return factory;
    }


    /**
     * 返回全局的Application
     * @return 返回全局的Application
     */
    public static Application getApp()
    {
        return  Application.getInstance();
    }

    /**
     * 执行异步操作
     * @param runnable
     */
    public static  void runAsync(Runnable runnable)
    {
        getInstance().executor.execute(runnable);
    }


    /**
     * 返回一个全局的gson
     * @return 返回一个Gson
     */
    public static Gson getGson()
    {
        return factory.gson;
    }

    /**
     * 对错误的code进行res解析
     * @param rspModel  响应体
     * @param callback  回掉函数
     */
    public static void decodeRsqCode(RspModel rspModel , DataSource.Callback callback)
    {
            switch (rspModel.getCode()){
                case RspModel.SUCCEED:
                    return;
                case RspModel.ERROR_CREATE_USER:
                    decodeRspModel(R.string.data_rsp_error_create_user,callback);
                    break;
                case RspModel.ERROR_ACCOUNT_TOKEN:
                    decodeRspModel(R.string.data_rsp_error_account_token,callback);
                    loginOut();
                    break;
                case RspModel.ERROR_ACCOUNT_REGISTER:
                    decodeRspModel(R.string.data_rsp_error_account_register,callback);
                    break;
                case RspModel.ERROR_ACCOUNT_LOGIN:
                    decodeRspModel(R.string.data_rsp_error_account_login,callback);
                    break;
                case RspModel.ERROR_CREATE_GROUP:
                    decodeRspModel(R.string.data_rsp_error_create_group,callback);
                    break;
                default:
                    decodeRspModel(R.string.data_rsp_error_service,callback);
                    break;




            }
    }

    private static  void decodeRspModel(final  @StringRes int res,DataSource.Callback callback)
    {
        if(callback != null)
        {
            callback.onDataNotAvaliable(res);
        }
    }

    /**
     * 收到账户退出信息错误，进行推出操作
     */
    private static void loginOut()
    {

    }
}
