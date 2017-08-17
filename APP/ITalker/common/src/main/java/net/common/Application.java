package net.common;

import android.os.SystemClock;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;

/**
 * Created by CLW on 2017/8/15.
 */

public class Application extends android.app.Application {
    private static  Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    /**
     * 获取单例Application
     * @return
     */
    public static final Application getInstance()
    {
        return mApplication;
    }

    /**
     * 获取缓存地址
     * @return 返回缓存地址
     */
    public static File getCashDirFile()
    {
        return mApplication.getCacheDir();
    }

    /**
     * 获取得到头像的本地地址
     * @return  返回头像的缓存地址
     */
    public static File getPortraitTempFile()
    {
        //得到头像的目录的缓存地址
        File dir = new File(getCashDirFile(),"portrait");
        //创建所有的对应文件夹
        dir.mkdirs();
        File[] listFiles = dir.listFiles();
        //清空里面所有文件
        if(listFiles != null&& listFiles.length>0)
        {
            for(File file : listFiles)
            {
                file.delete();
            }
        }
        //获得一个时间戳的相对位置的file位置
        File file = new File(dir, SystemClock.currentThreadTimeMillis()+".jpg");
        return file.getAbsoluteFile();

    }


    /**
     * 获取录音的本地地址
     * @param isTemp 是否是缓存文件，True代表的是的，False代表不是
     * @return  返回录音的本地地址
     */
    public static File getAudioTepFile(boolean isTemp)
    {
        File dir = new File(getCashDirFile(),"audio");
        dir.mkdirs();
        File[] listFiles = dir.listFiles();
        if(listFiles != null && listFiles.length > 0)
        {
            for(File file : listFiles)
            {
                file.delete();
            }
        }
        File file = new File(dir,isTemp ? "temp.mp3" : SystemClock.currentThreadTimeMillis()+".mp3");
        return file.getAbsoluteFile();
    }

    /**
     * Toast的展示
     * @param msg 展示的String
     */
    public static void showToast(final String  msg)
    {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(mApplication,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Toast的展示
     * @param resId 展示的字符串资源的Id
     */
    public static  void  showToast(final int resId)
    {
        showToast(mApplication.getString(resId));
    }

}
