package net.italker.cilent;

import com.igexin.sdk.PushManager;


import net.common.app.Application;
import net.factory.present.present.Factory;

/**
 * Created by CLW on 2017/8/15.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Factory.setUp();
        //推送初始化
        PushManager.getInstance().initialize(this);

    }
}
