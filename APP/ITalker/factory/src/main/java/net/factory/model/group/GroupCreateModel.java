package net.factory.model.group;

import java.util.HashSet;
import java.util.Set;

/**
 * 群创建Model
 * Created by CLW on 2019/6/4.
 */

public class GroupCreateModel {
    private String name;
    private  String desc;
    private String picture;
    private Set<String> users = new HashSet<>();

    public GroupCreateModel(String name, String desc, String picture, Set<String> users) {
        this.name = name;
        this.desc = desc;
        this.picture = picture;
        this.users = users;
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

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
