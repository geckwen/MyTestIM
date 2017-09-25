package net.factory.present.present.user;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;

import net.common.factory.data.DataSource;
import net.common.factory.present.BasePresent;
import net.common.tools.UiShow;
import net.factory.R;
import net.factory.present.present.Factory;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;
import net.factory.model.user.UpdateInfoModel;
import net.factory.net.UploadHelper;
import net.factory.data.Helper.UpdateHelper;

/**
 * Created by CLW on 2017/9/9.
 */

public class UpUserInfoPrensent extends BasePresent<UpUserInfoContract.View> implements UpUserInfoContract.Present,
        DataSource.Callback<UserCard> {
    /**
     * 初始化view但是为了防止复写父类的构造函数所以用一个方法来接替注册
     *
     * @param view
     */
    public UpUserInfoPrensent(UpUserInfoContract.View view) {
        super(view);
    }


    /**
     * 进行数据更新
     * @param des 描述
     * @param portraitPath 头像地址
     * @param isWoMan 男女鉴别
     */
    @Override
    public void UpdateInfo(final String des, final String portraitPath, final boolean isWoMan) {
      start();
        final UpUserInfoContract.View view =getmView();
        if(TextUtils.isEmpty(portraitPath)&&TextUtils.isEmpty(des))
        {
            view.showError(R.string.data_account_update_invalid_parameter);
        }else{
            Factory.runAsync(new Runnable() {
                @Override
                public void run() {
                    String url =UploadHelper.uploadPortrait(portraitPath);
                    Log.e("TAG",String.format("url:%s",url));
                    if(TextUtils.isEmpty(url))
                    {
                        //上传失败
                        view.showError(R.string.data_upload_error);
                    }else
                    {
                        //构建Model
                        UpdateInfoModel updateInfoModel = new UpdateInfoModel("",url,des,isWoMan? User.SEX_WOMEN:User.SEX_MAN);
                        //进行网络更新数据请求
                        UpdateHelper.upDateInfo(updateInfoModel,UpUserInfoPrensent.this);
                    }

                }
            });
        }
    }

    /**
     * 更新信息成功回掉
     * @param user
     */
    @Override
    public void onDataLoader(UserCard user) {
        final UpUserInfoContract.View view = getmView();
        if(view == null)
            return;
       UiShow.showRes(new Runnable() {
           @Override
           public void run() {
               view.UpdateInfoSuccesss();
           }
       });
    }

    @Override
    public void onDataNotAvaliable(final @StringRes int res) {
        final  UpUserInfoContract.View view = getmView();
        if (view == null)
            return;
        UiShow.showRes(new Runnable() {
            @Override
            public void run() {
                view.showError(res);
            }
        });
    }
}
