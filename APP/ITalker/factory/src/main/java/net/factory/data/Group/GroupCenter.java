package net.factory.data.Group;

import net.factory.model.card.GroupCard;
import net.factory.model.card.GroupMemberCard;

/**
 * Created by CLW on 2017/9/22.
 */

public interface GroupCenter {
    //处理群
    void dispatch(GroupCard... groupCards);
    //处理群用户
    void dispatch(GroupMemberCard... groupMemberCards);

}
