package net.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import net.common.R;

/**
 * Created by CLW on 2017/9/14.
 */

public abstract  class ToolBarActivity extends  BaseActivity {
    protected Toolbar toolbar;

    @Override
    public void initWidget() {
        super.initWidget();
        //进行初始化
        initToolBar((Toolbar) findViewById(R.id.toolbar));
    }

    /**
     * 初始化toolbar
     * @param toolbar 进行初始化toolbar
     */
    public void initToolBar(Toolbar toolbar)
    {
        this.toolbar = toolbar;
        if(toolbar!=null)
        {
            setSupportActionBar(toolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack()
    {
        ActionBar actionBar = getSupportActionBar();
        //当用户进行点击左上角按钮时,可以实现返回
        if(actionBar!=null)
        {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            //用 ActionBar 设置返回图标
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
