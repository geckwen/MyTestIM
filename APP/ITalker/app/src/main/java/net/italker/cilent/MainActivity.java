package net.italker.cilent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
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
import net.italker.cilent.fragment.main.ActivityFragment;
import net.italker.cilent.fragment.main.ContactFragment;
import net.italker.cilent.fragment.main.GroupFragment;
import net.italker.cilent.helper.NavHelper;
import net.qiujuer.genius.ui.Ui;

import java.util.Objects;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
NavHelper.OnTabChangeListener<Integer>{


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

    NavHelper<Integer> mnavHelper;

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
        mnavHelper=new NavHelper<Integer>(getSupportFragmentManager(),this,R.id.lay_container,this);
        //添加各个fragment进去
        mnavHelper.addTabs(R.id.action_home,new NavHelper.Tab<Integer>(ActivityFragment.class, R.string.action_home))
        .addTabs(R.id.action_group,new NavHelper.Tab<Integer>(GroupFragment.class,R.string.action_group)).
                addTabs(R.id.action_contact,new NavHelper.Tab<Integer>(ContactFragment.class,R.string.action_contact));
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
        //接管menu
        Menu menu = mnavigation.getMenu();
        //触发第一次的home
        menu.performIdentifierAction(R.id.action_home,0);
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


    /**
     * NavHelper处理方法回调
     * @param newTab 新的Tab
     * @param oldTab 旧的Tab
     */
    @Override
    public void onTabListener(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
             //取出我们额外的字段
             mtextTile.setText(newTab.extra);

            //对浮动按钮进行隐藏和显示动画
             float transY = 0;
             float rotation = 0;
           if(Objects.equals(newTab.extra,R.string.title_home))
           {
               transY = Ui.dipToPx(getResources(),76);
           }
           else
           {
               if(Objects.equals(newTab.extra,R.string.action_group))
               {
                 mbtnAction.setImageResource(R.drawable.ic_group_add);
                   rotation = -360;
               }
               else
               {
                   mbtnAction.setImageResource(R.drawable.ic_contact_add);
                   rotation = 360;
               }
           }
            //按钮切换动画
           mbtnAction.animate()
                   .rotation(rotation)
                   .translationYBy(transY)
                   .setDuration(480).start();
    }
}
