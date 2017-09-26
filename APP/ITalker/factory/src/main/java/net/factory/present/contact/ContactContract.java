package net.factory.present.contact;

import net.common.factory.present.BaseContract;
import net.factory.model.db.User;

/**
 * Created by CLW on 2017/9/17.
 */

public interface ContactContract {
    interface Present extends BaseContract.Present{

    }

    interface ContactView extends  BaseContract.RecycleView<Present,User>{

    }
}