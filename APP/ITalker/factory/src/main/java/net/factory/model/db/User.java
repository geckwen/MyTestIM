package net.factory.model.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import net.common.model.Author;
import net.factory.utils.DiffUiDataCallback;

import java.util.Date;
import java.util.Objects;

/**
 * Created by CLW on 2017/8/28.
 */

@Table(database = AppDatabase.class)
public class User extends BaseDbModel<User> implements Author{
    public   static  final  int SEX_MAN = 1;
    public  static  final  int SEX_WOMEN = 2;

    @PrimaryKey
    private String id;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String portrait;
    @Column
    private String desc;
    @Column
    private int sex = SEX_WOMEN;
    @Column
    //我对某人的备注信息，也应该写入数据库
    private String ailas;
    @Column
    // 用户关注人的数量
    private int follows;

    @Column
    // 用户粉丝的数量
    private int following;
    @Column
    // 我与当前User的关系状态，是否已经关注了这个人
    private boolean isFollow;
    @Column
    // 用户信息最后的更新时间
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getAilas() {
        return ailas;
    }

    public void setAilas(String ailas) {
        this.ailas = ailas;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", portrait='" + portrait + '\'' +
                ", desc='" + desc + '\'' +
                ", sex=" + sex +
                ", ailas='" + ailas + '\'' +
                ", follows=" + follows +
                ", following=" + following +
                ", isFollow=" + isFollow +
                ", modifyAt=" + modifyAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
            return !id.equals(user.id)?false:true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + portrait.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + sex;
        result = 31 * result + ailas.hashCode();
        result = 31 * result + follows;
        result = 31 * result + following;
        result = 31 * result + (isFollow ? 1 : 0);
        result = 31 * result + modifyAt.hashCode();
        return result;
    }

    @Override
    public boolean isSame(User old) {
      return  this==old|| Objects.equals(id,old.getId());
    }

    @Override
    public boolean isUiContentSame(User old) {
       return  this==old||(
               Objects.equals(name,old.name)
               &&Objects.equals(portrait,old.portrait)
               &&Objects.equals(sex,old.sex)
               &&Objects.equals(desc,old.desc)
               &&Objects.equals(isFollow,old.isFollow)
               );
    }
}
