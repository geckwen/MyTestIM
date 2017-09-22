package net.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
import java.util.Objects;

/**
 * Created by CLW on 2017/9/22.
 */

@Table(database =  AppDatabase.class)
public class GroupMember extends BaseModel {
    public static final int NOTIFY_LEVEL_INVALID = -1; // 关闭消息
    public static final int NOTIFY_LEVEL_NONE = 0; // 正常

    @PrimaryKey
    private String id;
    @Column
    private String alias;

    @Column
    private boolean isAdmin;
    @Column
    private boolean isOwner;
    @Column
    private Date updateAt ;
    @ForeignKey(tableClass = Group.class,stubbedRelationship = true)
    private Group group;
    @ForeignKey(tableClass = User.class,stubbedRelationship = true)
    private User user;



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



    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupMember that = (GroupMember) o;

        return isAdmin == that.isAdmin
                && isOwner == that.isOwner
                && Objects.equals(id, that.id)
                && Objects.equals(alias, that.alias)
                && Objects.equals(updateAt, that.updateAt)
                && Objects.equals(group, that.group)
                && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
