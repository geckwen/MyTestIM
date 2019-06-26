package net.factory.present.group;

import net.common.factory.present.BaseContract;
import net.factory.model.view.MemberUserModel;

/**
 * 群成员的契约
 * Created by CLW on 2019/6/20.
 */

public class GroupMemberContract {
   public interface  Presenter extends BaseContract.Present{
        void refresh();
    }

    //界面
    public interface View extends BaseContract.RecycleView<Presenter,MemberUserModel>{
        String getGroupId();
    }
}
