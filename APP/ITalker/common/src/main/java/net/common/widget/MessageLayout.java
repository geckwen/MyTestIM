package net.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import net.qiujuer.widget.airpanel.AirPanelLinearLayout;

/**
 * Created by CLW on 2017/9/29.
 */

public class MessageLayout  extends AirPanelLinearLayout {
    public MessageLayout(Context context) {
        super(context);
    }

    public MessageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MessageLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
        {
            insets.top=0;
            insets.left=0;
            insets.right=0;
        }
        return super.fitSystemWindows(insets);
    }
}
