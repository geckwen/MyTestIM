package net.factory.present.group;

import net.common.factory.present.BaseContract;
import net.common.model.Author;

/**
 * 群创建的契约
 * Created by CLW on 2019/5/31.
 */

public interface GroupCreateContract {
    interface  Presenter extends BaseContract.Present{
        //创建
        void create(String name,String desc,String picture);
        //更改选中
        void changeSelect(ViewModel model,boolean isSelected);
    }
    interface View extends BaseContract.RecycleView<Presenter,ViewModel>{
        //创建成功
        void onCreateSucceed();
    }


    public class ViewModel{
        public Author author;
        //是否选中
        public boolean isSelected;
    }

}
