package net.factory.data.user;

import net.factory.model.card.UserCard;

/**
 * 用户中心
 * Created by CLW on 2017/9/22.
 */

public interface UserCenter {
    void dispatch(UserCard... userCards);
}
