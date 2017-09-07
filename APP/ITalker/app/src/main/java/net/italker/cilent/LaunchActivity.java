package net.italker.cilent;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;

import net.common.app.BaseActivity;
import net.factory.persistence.Account;
import net.italker.cilent.activity.AccountActivity;
import net.italker.cilent.activity.MainActivity;
import net.italker.cilent.fragment.assist.PermissionFragment;
import net.qiujuer.genius.res.Resource;
import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * Created by CLW on 2017/8/20.
 */

public class LaunchActivity extends BaseActivity {
    private ColorDrawable mBgDrawable;
    private Context mContext;
    private volatile int testCount;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        View root = findViewById(R.id.activity_launch);
       //获取颜色
        int color = getResources().getColor(R.color.colorPrimary);
        //int color =UiCompat.getColor(getResources(),R.color.colorPrimary);
        //创建一个drawable
        mBgDrawable = new ColorDrawable(color);
        //为背景设置颜色
        root.setBackground(mBgDrawable);
        mContext = this;
    }

    @Override
    public void initData() {
        super.initData();
        startAnimation(0.5f, new Runnable() {
            @Override
            public void run() {
                waitPushIdRecevice();
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    /**
     * 等待50%等待个推为我们PushId设置值
     */
    private void waitPushIdRecevice()
    {

        if(Account.isAccount()) {
            //已经登陆判定是否绑定
            //没有绑定则等待广播进行绑定
            if(Account.isBind()) {
                skip();
                return;
            }
        }else {
            //没有登录
            //没有登陆则不能绑定PushId
            if(!TextUtils.isEmpty(Account.getPushId())) {
                skip();
                return;
            }
        }
        skip();
    }
    private void skip()
    {
        startAnimation(1.0f, new Runnable() {
                    @Override
                    public void run() {
                        realSkip();
                    }
        });
    }

    /**
     * 真正的跳转,这里进行权限检测
     */
    private void realSkip()
    {
        if(PermissionFragment.haveAllPermission(mContext,getSupportFragmentManager()))
        {
            if(Account.isAccount()){
                MainActivity.show(mContext);
            }else {
                AccountActivity.onShow(mContext);
            }
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 给背景设置一个动画
     * @param endProgress 动画结束的进度
     * @param endCallback  动画结束回掉
     */
    private void startAnimation(float endProgress,final  Runnable endCallback)
    {
        //获取颜色
        int finalColor = getResources().getColor(R.color.white);
        //运算当前的颜色
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int endColor = (int) evaluator.evaluate(endProgress,mBgDrawable.getColor(),finalColor);
        //第一个为自己,第二个为变化的类型,第三个为怎样变化,第4个为结束值
        ValueAnimator animator = ObjectAnimator.ofObject(this,property,evaluator,endColor);


        animator.setDuration(1500);//动画时间
        animator.setIntValues(mBgDrawable.getColor(),endColor);//动画的起始到结束
        //结束时回掉
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                endCallback.run();
            }
        });
        animator.start();

    }

    private final Property<LaunchActivity,Object> property = new Property<LaunchActivity, Object>(Object.class,"color") {
        @Override
        public void set(LaunchActivity object, Object value) {
            object.mBgDrawable.setColor((Integer) value);
        }

        @Override
        public Object get(LaunchActivity object) {
            return object.mBgDrawable.getColor();
        }
    };
}
