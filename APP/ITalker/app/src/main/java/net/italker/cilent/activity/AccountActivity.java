package net.italker.cilent.activity;


import android.content.Context;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.common.app.BaseActivity;
import net.common.app.BaseFragment;
import net.italker.cilent.R;
import net.italker.cilent.fragment.account.AccountTrigger;
import net.italker.cilent.fragment.account.LoginFragment;
import net.italker.cilent.fragment.account.RegisterFragment;

import butterknife.BindView;


/**
 * 登陆activity
 * Created by CLW on 2017/8/13.
 */

public class AccountActivity extends BaseActivity implements AccountTrigger {

    ImageView mImageView ;

    private  BaseFragment mCurrentFragment;
    private BaseFragment mLoginFragment;
    private BaseFragment mRegisterFragment;

    /**
     * 账户显示activity界面
     * @param context
     */
    public static void onShow(Context context)
    {
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    public void initWidget() {
        //初始化fragment
        mLoginFragment= new LoginFragment();
        mCurrentFragment = mLoginFragment;
        getSupportFragmentManager()
               .beginTransaction()
               .add(R.id.lay_container,mCurrentFragment)
               .commit();
        mImageView = (ImageView) findViewById(R.id.im_bg);
        //初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView,GlideDrawable>(mImageView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                //获取glide的drawable
                Drawable drawable = resource.getCurrent();
             /*   //使用适配器进行包装
                drawable = DrawableCompat.wrap(drawable);
                //设置着色效果和颜色，蒙板模式
                drawable.setColorFilter(UiCompat.getColor(getResources(),R.color.colorAccent),PorterDuff.Mode.SCREEN);*/
                //设置给ImagerView
                this.view.setImageDrawable(drawable);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 进行fragment切换
     */
    @Override
    public void trigger() {
        BaseFragment fragment;
        if(mCurrentFragment==mLoginFragment)
        {
            //第一次初始化
            if(mRegisterFragment==null)
            {
                mRegisterFragment = new RegisterFragment();
            }
            fragment = mRegisterFragment;
        }
        else{
            fragment = mLoginFragment;
        }
        //重新赋值
        mCurrentFragment = fragment;
        //进行fragment的切换
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.lay_container,mCurrentFragment).commit();
    }
}
