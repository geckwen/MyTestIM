package net.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import net.common.widget.convention.PlaceHolderView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 基本的activity
 * Created by CLW on 2017/7/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected PlaceHolderView mplaceHolderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化界面未开始前初始化窗口
        initWindow();
        if(initArgs(getIntent().getExtras())){
            setContentView(getContentLayoutId());
            initBefore();
            initWidget();
            initData();
        }
        else{
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected  void initWindow()
    {

    }

    /**
     * 在一切初始化之前
     */
    protected  void initBefore()
    {

    }


    /**
     * 初始化相关参数
     * @param bundle 参数Bundel
     * @return 如果正确返回true,错误返回false
     */
    protected boolean initArgs(Bundle bundle)
    {
        return  true;
    }

    /**
     * 得到当前资源文件的ID
     * @return
     */
    public abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    public void initWidget()
    {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    public void initData()
    {

    }


    @Override
    public boolean onSupportNavigateUp() {
        //当用户点击界面导航的返回时,则将该activiry给finish()
        finish();
        return super.onSupportNavigateUp();
    }


    /**
     *  点击物理键返回时，需要判断里面是否有要finish()
     */
    @Override
    public void onBackPressed() {
        //获得当前activity下的fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        //判断fragments是否为空
        if(fragments != null&&fragments.size()>0)
        {
            for(Fragment fragment:fragments)
            {
                //判断是否是我们能够处理的BasseFragment
                if(fragment instanceof BaseFragment)
                {
                    //判断是否拦截了onBackPressed()
                    if(((BaseFragment)fragment).onBackPressed())
                        return;
                }
            }
        }

        finish();
        super.onBackPressed();
    }

    /**
     * 设置占位布局
     * @param placeHolderView 继承类占位布局的view
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView)
    {
        this.mplaceHolderView = placeHolderView;
    }
}
