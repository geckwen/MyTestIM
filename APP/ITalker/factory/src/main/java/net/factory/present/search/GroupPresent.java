package net.factory.present.search;

import android.support.annotation.StringRes;

import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.factory.model.card.UserCard;

/**
 * Created by CLW on 2017/9/15.
 */

public class GroupPresent extends BasePresent<SearchContract.GroupView> implements SearchContract.Present,DataSource.Callback<UserCard> {

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public GroupPresent(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }

    @Override
    public void onDataLoader(UserCard user) {

    }

    @Override
    public void onDataNotAvaliable(@StringRes int res) {

    }
}
