package net.factory.model.view;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

import net.common.model.Author;
import net.factory.model.db.AppDatabase;

/**
 * Created by CLW on 2019/6/3.
 */

@QueryModel(database = AppDatabase.class)
public class UserSampleModel implements Author {
    @Column
    public String id;
    @Column
    public String name;
    @Column
    public String portrait;


    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public void setPhone(String phone) {

    }

    @Override
    public String getPortrait() {
        return null;
    }

    @Override
    public void setPortrait(String portrait) {

    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public void setDesc(String desc) {

    }
}
