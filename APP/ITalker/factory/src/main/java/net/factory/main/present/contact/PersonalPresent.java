package net.factory.main.present.contact;

import android.support.annotation.StringRes;

import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.model.Author;
import net.common.tools.UiShow;
import net.common.widget.recycle.a.PortraitView;
import net.factory.main.Factory;
import net.factory.model.db.User;
import net.factory.net.contact.ContactHelper;
import net.factory.persistence.Account;

/**
 * Created by CLW on 2017/9/20.
 */

public class PersonalPresent extends BasePresent<PersonalContract.View>  implements PersonalContract.Present{

    private User user;

    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public PersonalPresent(PersonalContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        Factory.runAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view  = getmView();
                if(view!=null){
                String id = view.getUserId();
                User user = ContactHelper.searchIdFirstNet(id);
                onLoadActivity(view,user);
                }
            }
        });
    }

    /**
     * 进行主界面的切换实现
     * @param view view
     * @param user 用户
     */
    private void onLoadActivity(final PersonalContract.View view , final User user)
    {
        this.user = user;
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        final boolean isFollow = isSelf || user.isFollow();
        final  boolean isAllowSayHello = !isSelf && isFollow;
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                view.onLoadDone(user);
                view.sayHello(isAllowSayHello);
                view.setFollowStatus(isFollow);
            }
        });
    }

    @Override
    public User getAuthor() {
        return user;
    }


}
