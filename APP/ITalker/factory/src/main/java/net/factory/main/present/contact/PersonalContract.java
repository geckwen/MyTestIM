package net.factory.main.present.contact;

import net.common.factory.present.BaseContract;
import net.common.model.Author;
import net.factory.model.db.User;

/**
 * Created by CLW on 2017/9/20.
 */

public interface PersonalContract {

    interface  Present extends BaseContract.Present{
            User getAuthor();
    }

    interface  View extends  BaseContract.View<Present>{
            String getUserId();
            //数据加载成功
            void onLoadDone(User user);
            //是否可以发起聊天
            void sayHello(boolean isFollow);
            //设置关注状态
            void setFollowStatus(boolean isFollow);
    }


}
