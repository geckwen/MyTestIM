package net.italker.cilent.fragment.media;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import net.common.tools.UiTools;
import net.common.widget.recycle.GalleyView;
import net.italker.cilent.R;
import net.qiujuer.genius.ui.Ui;

/**
 * 图片选择fragment
 */
public class GalleyFragment extends BottomSheetDialogFragment implements GalleyView.SelectListener {
    private GalleyView mGalley;
    private OnSelectListener mOnSelectListener;

    public GalleyFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //默认的Dialog
        return new TranStatusBottomSheetDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =inflater.inflate(R.layout.fragment_galley, container, false);
        //获取我们的GalleryView
        mGalley = (GalleyView) root.findViewById(R.id.galleryView);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGalley.setUp(getLoaderManager(),this);
    }

    @Override
    public void onSelectChange(int count) {
        //如果有选中照片则进行隐藏
        if(count>0)
            dismiss();
            if(mOnSelectListener != null)
            {       //得到全部选中的图片的路径
                    String[] paths = mGalley.getSelectPath();
                    //返回第一张
                    mOnSelectListener.onSelectImage(paths[0]);
                    //取消和唤起者之间的应用，加快内存的回收
                    mOnSelectListener=null;
            }
    }

    /**
     * 设置监听事件
     * @param mOnSelectListener 监听器
     * @return 返回自己
     */
    public GalleyFragment registerListener(OnSelectListener mOnSelectListener)
    {
        this.mOnSelectListener = mOnSelectListener;
        return this;
    }

    /**
     * 选中图片的监听器
     */
    public interface OnSelectListener{
        void onSelectImage(String path);
    }

    /**
     * 解决顶部变黑写的
     */
    public  static class TranStatusBottomSheetDialog extends BottomSheetDialog{

        public TranStatusBottomSheetDialog(@NonNull Context context) {
            super(context);
        }

        public TranStatusBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
            super(context, theme);
        }

        protected TranStatusBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            final Window window =getWindow();
            if (window == null)
                return ;
            //得到屏幕高度
            int screenHeight = UiTools.getScreenHeight(getOwnerActivity());
            //得到状态栏的高度
            int statusHeight = UiTools.getStatusHeight(getOwnerActivity());
            //计算dialog的高度并且设置
            int dialogHeight = screenHeight - statusHeight ;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,dialogHeight<=0 ? ViewGroup.LayoutParams.MATCH_PARENT:dialogHeight);
        }
    }
}
