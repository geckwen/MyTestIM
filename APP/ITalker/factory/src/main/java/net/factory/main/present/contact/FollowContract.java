package net.factory.main.present.contact;

import net.common.factory.present.BaseContract;
import net.factory.model.card.UserCard;

/**
 * 关注人接口
 * Created by CLW on 2017/9/17.
 */

public interface FollowContract {
    interface  Present extends BaseContract.Present{
        //关注操作
        void follow(String followId);
    }
    interface FollowView extends BaseContract.View<Present>{
        //网络请求成功
        void followDone(UserCard userCard);
    }

}
