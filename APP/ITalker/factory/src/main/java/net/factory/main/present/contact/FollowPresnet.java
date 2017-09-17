package net.factory.main.present.contact;

import android.support.annotation.StringRes;

import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.tools.UiShow;
import net.factory.model.card.UserCard;
import net.factory.net.account.UpdateHelper;

/**
 * Created by CLW on 2017/9/17.
 */

public class FollowPresnet extends BasePresent<FollowContract.FollowView> implements FollowContract.Present,
        DataSource.Callback<UserCard> {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public FollowPresnet(FollowContract.FollowView view) {
        super(view);
    }

    @Override
    public void follow(String followId) {
        start();
        UpdateHelper.follow(followId,this);
    }

    @Override
    public void onDataLoader(final UserCard user) {
        final FollowContract.FollowView view = getmView();
        if(view != null) {
            UiShow.showRes(new Runnable() {
                @Override
                public void run() {
                    view.followDone(user);
                }
            });
        }
    }

    @Override
    public void onDataNotAvaliable(@StringRes final int res) {
        final FollowContract.FollowView view = getmView();
        if(view != null) {
            UiShow.showRes(new Runnable() {
                @Override
                public void run() {
                    view.showError(res);
                }
            });
        }
    }
}
