package net.common.factory.present;

import android.support.v7.util.DiffUtil;

import net.common.tools.UiShow;
import net.common.widget.recycle.RecycleAdapter;

import java.util.List;
/**
 * Created by CLW on 2017/9/24.
 */

public class BaseRecyclePresent<ViewModel,View extends BaseContract.RecycleView> extends BasePresent<View> {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public BaseRecyclePresent(View view) {
        super(view);
    }

    /**
     * 刷新数据并更新界面
     * @param dataList 数据集合
     */
    protected void refreshData(final List<ViewModel> dataList)
    {
         final  View view =getmView();
        if(view!=null)
        {
            UiShow.showRes(new Runnable() {
                @Override
                public void run() {
                    //基本的更新数据并刷新界面
                   RecycleAdapter<ViewModel> adapter = view.getRecycleAdapter();
                    adapter.replace(dataList);
                    view.onAdapterDataRefresh();
                }
            });
        }
    }


    /**
     * 数据刷新
     * @param dataList 数据集合
     * @param diffResult 比较的方法
     */
    protected void refreshData(final List<ViewModel> dataList,final DiffUtil.DiffResult diffResult)
    {
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                refreshDataOnUi(dataList,diffResult);
            }
        });
    }




    /**
     * 在主线程刷新界面
     * @param dataList 数据集合
     * @param diffResult 比较的方法
     */
    protected void refreshDataOnUi(final List<ViewModel> dataList, DiffUtil.DiffResult diffResult)
    {
         final  View view = getmView();
        if(view!=null) {
           RecycleAdapter<ViewModel> adapter = view.getRecycleAdapter();
            //改变数据但不进行界面刷新
            adapter.getDataList().clear();
            adapter.getDataList().addAll(dataList);
            view.onAdapterDataRefresh();
            //进行增量更新
            diffResult.dispatchUpdatesTo(adapter);
        }
    }
}
