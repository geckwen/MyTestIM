package net.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CLW on 2017/7/29.
 */

public  abstract class BaseFragment extends Fragment {
    protected  View mRoot;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRoot == null) {
            //初始化当前布局，但是不在创建时添加到container里边
            int layoutId = getContentLayoutId();
            View viewRoot = inflater.inflate(layoutId, container, false);
            initWidget(viewRoot);
            mRoot = viewRoot;
        }else{
            if(mRoot.getParent() != null)
            {
                //把当前mRoot从当前父控件中移除
                ((ViewGroup)(mRoot.getParent())).removeView(mRoot);
            }
        }
        return mRoot;
    }

    /** 初始化参数
     * @param 参数bundle
     */
    protected  void initArgs(Bundle bundle)
    {}
    /**
     * 获取加载界面文件的ID
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    public void initWidget(View root)
    {

    }

    /**
     * 初始化数据
     */
    public void initData()
    {

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
