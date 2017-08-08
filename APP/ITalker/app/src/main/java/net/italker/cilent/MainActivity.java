package net.italker.cilent;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import net.common.BaseActivity;
import net.common.widget.recycle.a.PortraitView;
import net.italker.cilent.helper.NavHelper;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.appbar)
    View mLayAppbar ;
    @BindView(R.id.image_portrait)
    PortraitView mimgePortrait;

    @BindView(R.id.image_search)
    ImageView mimageSearch;

   @BindView(R.id.text_tile)
    TextView mtextTile;

    @BindView(R.id.btn_action)
    FloatingActionButton mbtnAction;

    @BindView(R.id.navigation)
    BottomNavigationView mnavigation;

    NavHelper mnavHelper;

    @Override
    protected boolean initArgs(Bundle bundle) {
        return super.initArgs(bundle);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        mnavHelper=new NavHelper(mfragmentManager, containerId, mcontext);
        //添加底部按钮监控
        mnavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this).load(R.drawable.bg_src_morning).centerCrop().into(new ViewTarget<View,GlideDrawable>(mLayAppbar) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });


    }

    @Override
    public void initData() {
        super.initData();
    }


    /**
     * 当我们点击底部不同按钮，实现fragment的转变
     * @param item MenuItem
     * @return true代表我们处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //转接点击流到工具类进行处理
        return mnavHelper.performOnClickMenu(item.getItemId());
    }
}
