package net.factory.main;

import net.common.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 单例模式
 * Created by CLW on 2017/8/20.
 */

public class Factory {
    private final Executor executor;
    private static final  int THREAD_COUNT = 4;
    private static  final  Factory factory = new Factory();
    private Factory(){
        //创建一个线程池为4个.
        executor= Executors.newFixedThreadPool(4);
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
}
