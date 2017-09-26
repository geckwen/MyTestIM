package net.italker.cilent.fragment.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.common.app.Application;
import net.common.app.PresentFragment;
import net.common.widget.recycle.a.PortraitView;
import net.factory.present.user.UpUserInfoContract;
import net.factory.present.user.UpUserInfoPrensent;
import net.italker.cilent.R;
import net.italker.cilent.activity.MainActivity;
import net.italker.cilent.fragment.media.GalleyFragment;
import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends PresentFragment<UpUserInfoContract.Present> implements UpUserInfoContract.View{
    @BindView(R.id.user_des)
    EditText mUserDes;

    @BindView(R.id.usr_sex)
    ImageView mUserSex;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.loading)
    Loading mLoading;

    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private String mPortraitPath;

    private boolean isWoMan = true;

    @Override
    protected UpUserInfoContract.Present initPresent() {
        return new UpUserInfoPrensent(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }


    /**
     * 头像点击
     */
    @OnClick(R.id.im_portrait)
     void onPortraitClick()
     {
        new GalleyFragment().registerListener(new GalleyFragment.OnSelectListener() {
            @Override
            public void onSelectImage(String path) {
                UCrop.Options options = new UCrop.Options();
                //设置图片处理格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //设置图片压缩的精度
                options.setCompressionQuality(96);
                //设置图片缓存的位置
                File dpath = Application.getPortraitTempFile();
                UCrop.of(Uri.fromFile(new File(path)),Uri.fromFile(dpath))
                        .withAspectRatio(1,1)   //1:1比例
                        .withMaxResultSize(520,520) //返回最大的尺寸
                        .withOptions(options)
                        .start(getActivity());
            }//show里面建议使用getChildFragmentManager(),因为Activity会造成一系列的麻烦
        }).show(getChildFragmentManager(),
                GalleyFragment.class.getName());
     }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Urix
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**加载头像并上传头像
     * @param resultUri ucrop的图片地址
     */
    private void loadPortrait(Uri resultUri)
    {
        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
        mPortraitPath = resultUri.getPath();
        final String localPath = resultUri.getPath();
        Log.e("TAG",String.format("localPath:%s",localPath));

    }

    /**
     * 更改性别
     */
     @OnClick(R.id.usr_sex)
     void onChangerSex()
     {
         isWoMan= !isWoMan;
         Drawable drawable = getResources().getDrawable(isWoMan?R.drawable.ic_sex_woman:R.drawable.ic_sex_man);
         mUserSex.setImageDrawable(drawable);
         //设置层级关系
         mUserSex.getBackground().setLevel(isWoMan?1:0);
     }

    @OnClick(R.id.btn_submit)
    void onSubmitOnclick()
    {
        //获得相对应的值
        String des = mUserDes.getText().toString();
        mPresent.UpdateInfo(des,mPortraitPath,isWoMan);
    }
    @Override
    public void showError(@StringRes int str) {
        super.showError(str);
        //当显示错误时触发
        //停止loading
        mLoading.stop();
        //让控件可以输入
        mUserDes.setEnabled(true);
        mUserSex.setEnabled(true);
        mPortrait.setEnabled(true);
        //让提交按钮可以再次提交
        mBtnSubmit.setEnabled(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        //开始loading
        mLoading.start();
        //让控件不可以输入
        mUserDes.setEnabled(false);
        mUserSex.setEnabled(false);
        mPortrait.setEnabled(false);
        //让提交按钮不可以再次提交
        mBtnSubmit.setEnabled(false);
    }

    /**
     * 信息注册成功后进入主界面
     */
    @Override
    public void UpdateInfoSuccesss() {
        MainActivity.show(getActivity());
        getActivity().finish();
    }

}
