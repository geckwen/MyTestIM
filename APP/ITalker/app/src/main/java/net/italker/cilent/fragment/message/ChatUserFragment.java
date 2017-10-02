package net.italker.cilent.fragment.message;


import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import net.common.app.BaseFragment;
import net.common.app.TextAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.model.db.User;
import net.italker.cilent.R;
import net.italker.cilent.activity.PersonalActivity;
import net.italker.cilent.activity.UserActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends ChatFragment {
    @BindView(R.id.im_portrait)
    PortraitView mPortraitView;

    @BindView(R.id.txt_send)
    EditText mContent;

    @BindView(R.id.btn_submit)
    ImageView submit;

    @BindView(R.id.btn_record)
    ImageView mRecord;

    @BindView(R.id.btn_emoj)
    ImageView mEmoj;

    MenuItem mUserMenusInfo;

    public ChatUserFragment() {
        // Required empty public constructor
    }

    /**
     * 初始化toolbar
     */
    @Override
    protected void initToolBar() {
        super.initToolBar();
        Toolbar toolbar = mToolbar;
        //加载icon
        toolbar.inflateMenu(R.menu.chat_user_item);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_personal) {
                    onPortraitClick();
                }
                return false;
            }
        });
        mUserMenusInfo=toolbar.getMenu().findItem(R.id.action_personal);

    }


    /**
     * 初始化输入框
     */
    @Override
    protected void initEditContent()
    {
        mContent.addTextChangedListener(new TextAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean isNoEmpty = !content.isEmpty();
                submit.setActivated(isNoEmpty);
            }
        });
    }


    @OnClick(R.id.im_portrait)
    void onPortraitClick()
    {
        PersonalActivity.show(getContext(),mReceiverId);
    }

    @OnClick(R.id.btn_record)
    void onRecord()
    {
        //TODO 录音
    }

    @OnClick(R.id.btn_emoj)
    void onEmoj()
    {

    }

    @OnClick(R.id.btn_submit)
    void onSendClick(){
        if(submit.isActivated())
        {
            //TODO 发送
        }else{
            //TODO  分享
        }
    }

    void onMoreClick(){

    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }


    /**
     * 进行高度的运算,透明我们的头像
     * @param appBarLayout appbarLayout
     * @param verticalOffset 当高度变化时 会传递进来
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        View view = mPortraitView;
        MenuItem menuItem = mUserMenusInfo;
        if(menuItem==null||view==null)
            return;

        if(verticalOffset==0)
        {
            //完全展开
            view.setVisibility(View.VISIBLE);
            //设置缩放
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);

            mUserMenusInfo.setVisible(false);
            mUserMenusInfo.getIcon().setAlpha(0);
        }else{
            //abs计算
            verticalOffset = Math.abs(verticalOffset);
            final int totalScrollRange= appBarLayout.getTotalScrollRange();
            if(verticalOffset>=totalScrollRange)
            {
                //完全关闭
                view.setVisibility(View.INVISIBLE);
                //设置缩放
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);

                mUserMenusInfo.setVisible(true);
                mUserMenusInfo.getIcon().setAlpha(255);
            }else{
                //中间状态
                float progress =1- verticalOffset/(float)totalScrollRange;
                view.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);
                mUserMenusInfo.setVisible(true);
                mUserMenusInfo.getIcon().setAlpha((int) (255-255*progress));
            }
        }
    }
}
