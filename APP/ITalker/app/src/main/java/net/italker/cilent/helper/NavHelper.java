package net.italker.cilent.helper;

import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

/**
 * fragment调度的工具类
 * Created by CLW on 2017/8/8.
 */

public class NavHelper<T> {
    //fragment集合
    private  final SparseArray<Tab<T>> tabs = new SparseArray<>();
    //初始化的必须参数
    private  final  FragmentManager  mfragmentManager;
    private  final Context mcontext;
    private  final  int containerId;
    private final OnTabChangeListener<T> mOntabChangeListener;
    //当前fragmen
    private Tab<T> currentTab;

    public NavHelper(FragmentManager mfragmentManager, Context mcontext, int containerId, OnTabChangeListener<T> mOntabChangeListener) {
        this.mfragmentManager = mfragmentManager;
        this.mcontext = mcontext;
        this.containerId = containerId;
        this.mOntabChangeListener = mOntabChangeListener;
    }

    /**
     * 用于添加tabs
     * @param menuId 所添加tab的id
     * @param tab 多添加的tab
     */
    public NavHelper<T> addTabs(int menuId,Tab<T> tab)
    {
        tabs.put(menuId,tab);
        return this;
    }

    /**
     * 获取当前的tab
     * @return 当前tab
     */
    public Tab<T> getCurrentTab()
    {
       return  currentTab;
    }

    /**
     * 执行点击菜单的操作
     * @param menuId 菜单的Id
     * @return 是否能够处理这个点击
     */
    public boolean performOnClickMenu(int menuId)
    {
        Tab<T> tab = tabs.get(menuId);
        if (tab != null)
        {
                selectTab(tab);
               return  true;
        }
        return false;
    }

    private void selectTab(Tab<T> tab)
    {
        Tab<T> oldTab = null;
        if(currentTab != null)
        {
            oldTab = currentTab;
            if(oldTab == tab)
            {
                notifyTab(oldTab);
                return ;
            }
        }
        currentTab = tab;
        doChangeTab(oldTab);
    }

    /**
     * fragment进行更换操作
     * @param oldTab 旧的fragment
     */
    private void doChangeTab(Tab<T> oldTab)
    {
       FragmentTransaction mfragmentTrasaction = mfragmentManager.beginTransaction();
      if(oldTab != null)
      {
          if(oldTab.fragment != null)
          {
              //从界面去除还在缓存
              mfragmentTrasaction.detach(oldTab.fragment);
          }
      }
      if(currentTab!=null)
      {
          if(currentTab.fragment == null)
          {
              //进行容器里面的创建
              Fragment mfragment = Fragment.instantiate(mcontext,currentTab.mclass.getName(),null);
              //缓存fragment
              currentTab.fragment=mfragment;
              mfragmentTrasaction.add(containerId,mfragment);
          }
          else
              mfragmentTrasaction.attach(currentTab.fragment);
      }
      mfragmentTrasaction.commit();
      notifyTabSelect(currentTab,oldTab);
    }

    /**
     * 通知界面已经更新了界面
     * @param newTab 新的界面
     * @param oldTab 旧的界面
     */
    private void notifyTabSelect(Tab<T> newTab,Tab<T> oldTab)
    {
        if(mOntabChangeListener!=null)
        {
            mOntabChangeListener.onTabListener(newTab, oldTab);
        }
    }
    private void notifyTab(Tab<T> tab)
    {
        //二次点击操作
    }

    /**
     * 注册点击Tab监听回掉
     * @param mOntabChangeListener
     */



    /**
     * 我们所有Tab的基础属性
     * @param <T> 额外的参数
     */
    public static class Tab<T>{
        public Tab(Class<? extends Fragment> mclass,T mextra){
            this.mclass=mclass ;
            this.extra= mextra ;
        }
        //Fragment对应的class信息
        public  Class<? extends Fragment> mclass;
        //额外的字段，由用户需要定义
        public T extra;
        Fragment fragment ;
    }


    /**
     * 定义更换Fragment事件回掉
     * @param <T>
     */
    public interface OnTabChangeListener<T>{
        void onTabListener(Tab<T> newTab,Tab<T> oldTab);
    }
}
