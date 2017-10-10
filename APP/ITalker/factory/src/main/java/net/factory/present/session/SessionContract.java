package net.factory.present.session;

import net.common.factory.present.BaseContract;
import net.factory.model.db.Session;

/**
 * Created by CLW on 2017/10/5.
 */

public interface SessionContract {
    interface Present extends BaseContract.Present{

    }

    interface View extends BaseContract.RecycleView<Present,Session>{

    }
}
