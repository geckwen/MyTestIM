package net.factory.present.message;

import android.view.View;

import net.common.factory.present.BaseContract;
import net.factory.model.db.Group;
import net.factory.model.db.Message;
import net.factory.model.db.User;
import net.factory.model.view.MemberUserModel;

import java.util.List;

/**
 * Created by CLW on 2017/10/3.
 */

public interface ChatContract {
    interface  Present extends BaseContract.Present{
            //发送消息
            void pushMsg(String msg);
            //发送语音
            void pushAudio(String path);
           //发送图片
            void pushPic(String[] paths);
            //重新发送
            boolean refresh(Message message);
    }
    //基础的view
    interface View<initModel> extends BaseContract.RecycleView<Present,Message>{
            void init(initModel model);
    }

    //人聊天的界面
    interface UserView extends View<User>{

    }
    //群聊天的界面
    interface  GroupView extends View<Group>{
        //显示管理员界面
        void showAdminOption(boolean isAdmin);
        //群信息界面
        void onInitGroupMembers(List<MemberUserModel> members,int moreCount);
    }
}
