package net.qintalk.italker.push.bean.user;


import com.google.gson.annotations.Expose;

import net.qintalk.italker.push.bean.db.User;
import net.qintalk.italker.push.utils.TextUtil;


/**
 * 用户更新信息，完善信息的Model
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public class UpdateInfoModel {
    @Expose
    private String name;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
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

    /**
     * 把当前的信息，填充到用户Model中
     * 方便UserModel进行写入
     *
     * @param user User Model
     * @return User Model
     */
    public User updateToUser(User user) {
        if (TextUtil.StringNotEmpty(name))
            user.setName(name);

        if (TextUtil.StringNotEmpty(portrait))
            user.setPortrait(portrait);

        if (TextUtil.StringNotEmpty(desc))
            user.setDescription(desc);

        if (sex != 0)
            user.setSex(sex);

        return user;
    }

    public static boolean check(UpdateInfoModel model) {
        // Model 不允许为null，
        // 并且只需要具有一个及其以上的参数即可
        return model != null
                && (!TextUtil.StringNotEmpty(model.name) ||
                !TextUtil.StringNotEmpty(model.portrait) ||
                !TextUtil.StringNotEmpty(model.desc) ||
                model.sex != 0);
    }

}
