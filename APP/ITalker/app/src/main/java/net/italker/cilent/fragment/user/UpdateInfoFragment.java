package net.italker.cilent.fragment.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.support.v4.app.Fragment;
import android.util.Log;


import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import net.common.app.Application;
import net.common.app.BaseFragment;
import net.common.widget.recycle.a.PortraitView;
import net.factory.main.Factory;
import net.factory.net.UploadHelper;
import net.italker.cilent.R;
import net.italker.cilent.fragment.media.GalleyFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends BaseFragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_update_info;
    }


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

    private void loadPortrait(Uri resultUri)
    {
        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(mPortrait);
        final String localPath = resultUri.getPath();
        Log.e("TAG",String.format("localPath:%s",localPath));
        Factory.runAsync(new Runnable() {
            @Override
            public void run() {
               String url = UploadHelper.uploadPortrait(localPath);
                Log.e("TAG",String.format("url:%s",url));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
