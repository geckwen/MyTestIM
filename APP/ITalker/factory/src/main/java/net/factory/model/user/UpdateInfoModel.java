package net.factory.model.user;


import net.factory.model.db.User;

/**
 * 用户更新信息，完善信息的Model
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class UpdateInfoModel {

    private String name;

    private String portrait;

    private String desc;

    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UpdateInfoModel(String name, String portrait, String desc, int sex) {
        this.name = name;
        this.portrait = portrait;
        this.desc = desc;
        this.sex = sex;
    }
}
