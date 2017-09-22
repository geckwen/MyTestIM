package net.factory.model.card;

import net.factory.model.db.Group;
import net.factory.model.db.User;

import java.util.Date;

/**
 * 用户卡片信息
 * Created by CLW on 2017/9/22.
 */

public class GroupCard {
    private String id;
    private String name;
    private String desc;
    private String picture;
    private String ownerId;
    private int notifyLevel;
    private Date joinAt;
    private Date modifyAt;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public Date getJoinAt() {
        return joinAt;
    }

    public void setJoinAt(Date joinAt) {
        this.joinAt = joinAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Group build(User user)
    {
        Group group  = new Group();
        group.setCreateAt(joinAt);
        group.setDescription(desc);
        group.setId(id);
        group.setName(name);
        group.setPicture(picture);
        group.setUpdateAt(modifyAt);
        group.setOwner(user);
        return group;
    }
}
