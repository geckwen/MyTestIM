package net.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by CLW on 2017/7/29.
 */

public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化界面未开始前初始化窗口
        initWindow();
        if(initArgs(getIntent().getExtras())){
            getContentLayoutId();
            initWidget();
            initData();
        }
        else{
            finish();
        }
        setContentView(getContentLayoutId());
    }

    /**
     * 初始化窗口
     */
    protected  void initWindow()
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

    }

    /**
     * 初始化数据
     */
    public void initData()
    {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //当用户点击界面导航的返回时,则将该activiry给finish()
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
