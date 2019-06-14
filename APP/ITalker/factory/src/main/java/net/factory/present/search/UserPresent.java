package net.factory.present.search;

import android.support.annotation.StringRes;

import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.tools.UiShow;
import net.factory.model.card.UserCard;
import net.factory.data.Helper.UpdateHelper;

import java.util.List;

import retrofit2.Call;

/**
 * Created by CLW on 2017/9/15.
 */

public class UserPresent extends BasePresent<SearchContract.UserView>
        implements SearchContract.Present,DataSource.Callback<List<UserCard>> {

    private Call searchCall;

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public UserPresent(SearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();
        //防止用户多次点击搜索导致搜索错乱
        Call call = searchCall;
        //如果有上一次请求,并且没有取消,则调用取消请求操作
        if(call!=null && !call.isCanceled()) {
            call.cancel();
        }
        searchCall = UpdateHelper.search(content,this);
    }



    @Override
    public void onDataNotAvaliable(@StringRes final int res) {
        final SearchContract.UserView view = getmView();
        if(view!=null) {
            UiShow.showRes(new Runnable() {
                @Override
                public void run() {
                    view.showError(res);
                }
            });
        }
    }


    @Override
    public void onDataLoader(final List<UserCard> user) {
        final SearchContract.UserView view = getmView();
        if(view!=null) {
            UiShow.showRes(new Runnable() {
                @Override
                public void run() {
                    view.onSearchDone(user);
                }
            });
        }
    }
}
