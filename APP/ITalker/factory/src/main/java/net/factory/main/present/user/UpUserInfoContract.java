package net.factory.main.present.user;

import net.common.factory.present.BaseContract;

/**
 * 用户信息更新契约
 * Created by CLW on 2017/9/9.
 */

public interface UpUserInfoContract  {
    interface View extends BaseContract.View<Present>{
        //更新信息成功
        void UpdateInfoSuccesss();
    }
    interface  Present extends  BaseContract.Present{
        //进行信息更新
        void UpdateInfo(String des,String portrait,boolean isMan);
    }

}
