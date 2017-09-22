package net.factory.model.card;

import net.factory.model.db.Group;
import net.factory.model.db.GroupMember;
import net.factory.model.db.User;

import java.util.Date;

/**
 * Created by CLW on 2017/9/22.
 */

public class GroupMemberCard {

    private String id;

    private String alias;



    private boolean isAdmin;

    private boolean isOwner;

    private Date updateAt ;

    private String groupId;

    private String userId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GroupMember build(Group group,User user)
    {
        GroupMember groupMember = new GroupMember();
        groupMember.setUpdateAt(updateAt);
        groupMember.setId(id);
        groupMember.setAlias(alias);
        groupMember.setGroup(group);
        group.setOwner(user);
        return  groupMember;
    }
}
