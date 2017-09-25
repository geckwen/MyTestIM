package net.factory.data.message;

import net.factory.model.card.MessageCard;
import net.factory.model.card.UserCard;

/**
 * Created by CLW on 2017/9/22.
 */

public interface MessageCenter {
    void dispatch(MessageCard... messageCards);
}
