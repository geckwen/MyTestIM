package net.factory.main.present.search;

import net.common.factory.present.BaseContract;
import net.factory.model.card.UserCard;

import java.util.List;

/**
 * Created by CLW on 2017/9/15.
 */

public interface SearchContract {
    interface Present extends BaseContract.Present{
        void search(String content);
    }
    interface UserView extends  BaseContract.View<Present>{
        void onSearchDone(List<UserCard> userCards);
    }
    interface  GroupView extends  BaseContract.View<Present>{
        void onSearchDone(List<UserCard> userCards);
    }
}
