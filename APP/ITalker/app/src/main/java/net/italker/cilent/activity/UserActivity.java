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
import net.factory.model.db.User;
import net.italker.cilent.R;
import net.italker.cilent.fragment.user.UpdateInfoFragment;

import butterknife.BindView;

public class UserActivity extends BaseActivity {
    @BindView(R.id.im_bg)
    ImageView mImBg;
    private BaseFragment mUpdateInfoFragment;

    /**进行跳转
     * @param context 传入的容器
     */
    public static void show(Context context)
    {
        context.startActivity(new Intent(context, UserActivity.class));
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mUpdateInfoFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction().add(R.id.lay_container,mUpdateInfoFragment)
                .commit();
        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView,GlideDrawable>(mImBg) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        mUpdateInfoFragment.onActivityResult(requestCode,resultCode,data);
    }
}
