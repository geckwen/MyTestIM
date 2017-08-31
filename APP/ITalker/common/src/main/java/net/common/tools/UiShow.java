package net.common.tools;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import net.common.app.Application;

/**
 * 异步UI显示
 * Created by CLW on 2017/8/30.
 */

public class UiShow {
    private static final Handler handle = new Handler(Looper.getMainLooper());

    /**
     * 显示通过传递一个runnable
     * @param runnable 额外的线程
     */
    public static void showRes(Runnable runnable)
    {
        handle.post(runnable);
    }

    /**
     * 展示UI
     * @param msg 需要展示的文字
     */
    public static void showString(String msg)
    {
        Toast.makeText(Application.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
}
