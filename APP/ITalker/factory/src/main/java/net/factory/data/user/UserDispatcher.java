package net.factory.data.user;

import net.factory.data.Helper.DBHelper;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * User分发usercard数据分发
 * Created by CLW on 2017/9/22.
 */

public class UserDispatcher implements UserCenter {
    private volatile static UserDispatcher instance;

    private static final Executor executor = Executors.newSingleThreadExecutor();

    private UserDispatcher(){}

    public static UserDispatcher getInstance()
    {
        if (instance==null)
        {
            synchronized (UserDispatcher.class)
            {
                if (instance==null)
                {
                    instance= new UserDispatcher();
                }
            }
        }
        return instance;
    }


    @Override
    public void dispatch(UserCard... userCards) {
        if(userCards==null||userCards.length==0)
            return;
        instance.executor.execute(new UserCardHelder(userCards));
    }


    private class UserCardHelder implements Runnable{
        private final UserCard[] userCards;
        public UserCardHelder(UserCard[] userCards){
            this.userCards = userCards;
        }

        @Override
        public void run() {

            //单线程开始调度
            List<User> userList = new ArrayList<>();
            for(UserCard userCard:userCards)
            {
                if(userCard==null||userCard.getId().isEmpty())
                    continue;
                User user = userCard.build();
                userList.add(user);
            }
            //进行数据存储,并进行异步的分发
            DBHelper.save(User.class,userList.toArray(new User[0]));
        }
    }
}
