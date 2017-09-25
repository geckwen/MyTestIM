package net.factory.present.present;

import android.support.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import net.common.app.Application;
import net.common.factory.data.DataSource;
import net.factory.R;
import net.factory.data.Group.GroupCenter;
import net.factory.data.Group.GroupDispatcher;
import net.factory.data.message.MessageCenter;
import net.factory.data.message.MessagerDiapatcher;
import net.factory.data.user.UserCenter;
import net.factory.data.user.UserDispatcher;
import net.factory.model.base.RspModel;

import net.factory.model.card.MessageCard;
import net.factory.persistence.Account;
import net.factory.utils.DBFlowExclusionStrategy;

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
                //设置一个过滤器,在数据库级别时不进行json转换
                .setExclusionStrategies(new DBFlowExclusionStrategy())
                .create();
    }

    public static Factory getInstance()
    {
        return factory;
    }

    public static void setUp()
    {

        FlowManager.init(new FlowConfig.Builder(getApp())
                .openDatabasesOnInit(true) //当数据库初始化就打开数据库
                .build());
        //持久化的数据初始化
        Account.load(getApp());
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
                    decodeRspModel(R.string.data_account_login_error_validate,callback);
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

    public static void disPatcherMessage(String message)
    {

    }


    public  static  UserCenter getUserCenter()
    {
        return UserDispatcher.getInstance();
    }

    public static MessageCenter getMessageCente()
    {
        return MessagerDiapatcher.getInstance();
    }

    public static GroupCenter getGroupCenter()
    {
        return GroupDispatcher.getInstance();
    }
}
