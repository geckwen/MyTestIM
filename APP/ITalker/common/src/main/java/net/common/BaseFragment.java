package net.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**v
 *
 * Created by CLW on 2017/7/29.
 */

public  abstract class BaseFragment extends Fragment {
    protected  View mRoot;
    protected Unbinder mRootBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRoot == null) {
            int layoutId = getContentLayoutId();
            //初始化当前布局，但是不在创建时添加到container里边
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
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        intent.put
        return mRoot;
    }

    /** 初始化参数
     * @param bundle 参数bundle
     */
    protected  void initArgs(Bundle bundle)
    {

    }
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
        mRootBinder=ButterKnife.bind(this,root);
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


    /**
     * 处理点击界面导航返回时调用
     * @return 逻辑处理成功返回true，不需要activity帮忙finish().
     * 返回false表示没有处理，activity继续走它的逻辑
     */
    public boolean onBackPressed(){
        return false;
    }
}
