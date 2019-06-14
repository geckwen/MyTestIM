package net.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import net.factory.data.Helper.GroupHelper;
import net.factory.model.view.MemberUserModel;
import net.factory.utils.DiffUiDataCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**群信息
 * Created by CLW on 2017/9/22.
 */

@Table(database = AppDatabase.class)
public class Group extends BaseDbModel<Group> {

    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String picture;
    @Column
    private int notifyLevel;
    @Column
    private Date createAt ;
    @Column
    private Date updateAt ;
    @ForeignKey(tableClass = User.class,stubbedRelationship = true)
    private User owner;

    //预留字段 用于显示
    public Object holder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return notifyLevel == group.notifyLevel
                && Objects.equals(name,group.getName())
                && Objects.equals(description,group.getDescription())
                && Objects.equals(picture,group.getPicture())
                && Objects.equals(id,group.getId())
                && Objects.equals(owner,group.getOwner())
                && Objects.equals(updateAt,group.getUpdateAt());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean isSame(Group old) {
        return this==old||Objects.equals(id,old.getId()) ;
    }

    @Override
    public boolean isUiContentSame(Group old) {
        return this==old||
                (       Objects.equals(name,old.getName())
                        && Objects.equals(description,old.getDescription())
                        && Objects.equals(picture,old.getPicture())
                        && Objects.equals(holder, old.holder)
                )     ;
    }

    private long groupMemberCount = -1;
    public long getGroupMemberCount() {
        if(groupMemberCount == -1)
        {
            //没有初始化
            groupMemberCount = GroupHelper.getMemberCount(id);
        }
        return groupMemberCount;
    }

    private    List<MemberUserModel> groupLatetyModels;

    /**
     * 获取群成员信息 只获取四个
     * @return
     */
    public  List<MemberUserModel> getLatetyGroupMember() {
        if(groupLatetyModels == null || groupLatetyModels.isEmpty())
        {
            //加载简单的群成员信息,返回四条,最多
            groupLatetyModels = GroupHelper.getMemberUser(id,4)
        }
        return userModels;
    }
}
