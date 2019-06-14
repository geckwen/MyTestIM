package net.factory.present.group;

import net.common.factory.present.BaseContract;
import net.factory.model.db.Group;
import net.factory.model.db.User;

/**
 * 群列表的契约
 * Created by CLW on 2017/9/17.
 */

public interface GroupContract {
    interface Present extends BaseContract.Present{

    }

    interface GroupView extends  BaseContract.RecycleView<Present,Group>{

    }
}
