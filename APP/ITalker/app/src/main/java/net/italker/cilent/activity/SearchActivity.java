package net.italker.cilent.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.common.app.BaseActivity;
import net.common.app.BaseFragment;
import net.common.app.ToolBarActivity;
import net.italker.cilent.R;
import net.italker.cilent.fragment.search.GroupSearchFragment;
import net.italker.cilent.fragment.search.UserSearchFragment;

public class SearchActivity extends ToolBarActivity{

    private static  final  String EXTRA_TYPE = "EXTRA_TYPE" ;
    //用户搜索
    public static final  int TYPE_USER =1;
    //群搜索
    public static  final  int TYPE_GROUP = 2;

    private  int type;

    private SearchFragment fragment;

    private UserSearchFragment mUserSearchFragment;

    private GroupSearchFragment mGroupSearchFragment;


    private BaseFragment mCurrentFragment;

    /**
     * 进行界面显示 type代表传递的形式
     * @param context 传递的容器
     * @param type 传递的类型
     */
    public static void show(Context context,int type)
    {
        Intent intent =new Intent(context,SearchActivity.class);
        intent.putExtra(EXTRA_TYPE,type);
        context.startActivity(intent);

    }

    /**
     * 只要是其中一种
     * @param bundle 参数Bundel
     * @return 返回一个true或者false
     */
    @Override
    protected boolean initArgs(Bundle bundle) {
        type = bundle.getInt(EXTRA_TYPE);
        return  type==TYPE_USER||type==TYPE_GROUP;
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_search;
    }

    /**
     * 界面转换
     */
    @Override
    public void initWidget() {
        super.initWidget();
        if(type == TYPE_GROUP) {
            mGroupSearchFragment = new GroupSearchFragment();
            fragment = mGroupSearchFragment;
            mCurrentFragment = mGroupSearchFragment;
        }else{
            mUserSearchFragment = new UserSearchFragment();
            fragment = mUserSearchFragment;
            mCurrentFragment = mUserSearchFragment;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lay_container,mCurrentFragment)
                .commit();
    }

    /**
     * 初始化菜单
     * @param menu 菜单
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_items,menu);
        //找到搜索菜单
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if(searchView!=null)
        {
            //拿到一个搜索的管理器
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //当点击按钮提交按钮的时候
                    search(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    //当文字改变的时候,不会及时的搜索，只有为null的时候搜索
                    if(TextUtils.isEmpty(newText)){
                        search("");
                        return  true;
                    }
                    return false;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 搜索的发起点
     * @param s 搜索的文字
     */
    private void search(String s) {
        fragment.search(s);
     }

    public interface SearchFragment
    {
        void search(String query);
    }
}
