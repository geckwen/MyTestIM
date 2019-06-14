package net.factory.model.view;

import com.raizlabs.android.dbflow.annotation.Column;

/**
 * 群成员对应的用户的信息表
 * Created by CLW on 2019/6/10.
 */

public class MemberUserModel {
    @Column
    public String userId; // User_id
    @Column
    public String name;  // User_name
    @Column
    public String alias; //Member_alias
    @Column
    public String portrait; //User_portrait

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
