package net.factory.data.Group;

import net.factory.data.Helper.DBHelper;
import net.factory.data.Helper.ContactHelper;
import net.factory.data.Helper.GroupHelper;
import net.factory.model.card.GroupCard;
import net.factory.model.card.GroupMemberCard;
import net.factory.model.db.Group;
import net.factory.model.db.GroupMember;
import net.factory.model.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by CLW on 2017/9/22.
 */

public class GroupDispatcher implements GroupCenter {
    private static final Executor executor = Executors.newSingleThreadExecutor();

    //单例模式
    private static  volatile  GroupDispatcher instance;
    private GroupDispatcher(){}
    public static GroupDispatcher getInstance()
    {
        if(instance==null){
            synchronized (GroupDispatcher.class) {
                if(instance==null){
                 instance= new GroupDispatcher();
                }
            }
        }
        return instance;
    }
    @Override
    public void dispatch(GroupCard... groupCards) {
        if(groupCards==null||groupCards.length==0)
            return;
        executor.execute(new GroupHelder(groupCards));
    }

    @Override
    public void dispatch(GroupMemberCard... groupMemberCards) {
        if(groupMemberCards==null || groupMemberCards.length==0)
            return;
        executor.execute(new GroupMemberHelder(groupMemberCards));
    }


    /**
     * 处理群
     */
    private class GroupHelder implements Runnable{
        private final GroupCard[] groupCards;

        public GroupHelder(GroupCard[] groupCards)
        {
            this.groupCards=groupCards;
        }

        @Override
        public void run() {
            List<Group> groups = new ArrayList<>();
            for(GroupCard groupCard : groupCards)
            {
                if(groupCard.getId().isEmpty()||groupCard==null)
                    continue;
                User user =ContactHelper.searchId(groupCard.getOwnerId());

                if(user!=null) {
                    Group group = groupCard.build(user);
                    groups.add(group);
                }
            }
            if(groups.size()>0)
                DBHelper.save(Group.class,groups.toArray(new Group[0]));
        }
    }

    /**
     * 处理群成员
     */
    private class GroupMemberHelder implements Runnable{
       private final GroupMemberCard[] groupMemberCards;
        public GroupMemberHelder(GroupMemberCard[] groupMemberCards){
            this.groupMemberCards = groupMemberCards;
        }

        @Override
        public void run() {
            List<GroupMember>groupMembers = new ArrayList<>();
            for(GroupMemberCard groupMemberCard : groupMemberCards)
            {
                if(groupMemberCard==null||groupMemberCard.getId().isEmpty())
                    continue;
                User user = ContactHelper.searchId(groupMemberCard.getUserId());
                Group group =GroupHelper.findById(groupMemberCard.getGroupId());
                if(user!=null&&group!=null) {
                    GroupMember groupMember =groupMemberCard.build(group,user);
                    groupMembers.add(groupMember);
                }
            }
            DBHelper.save(GroupMember.class,groupMembers.toArray(new GroupMember[0]));
        }
    }

}
