package net.italker.cilent.fragment.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.BaseFragment;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.model.db.Message;
import net.factory.model.db.User;
import net.factory.persistence.Account;
import net.italker.cilent.R;
import net.italker.cilent.activity.MessageActivity;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by CLW on 2017/9/29.
 */

public  abstract  class ChatFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener{
    protected String mReceiverId;



    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.appbar)
    AppBarLayout  mAppBarLayout;

    @BindView(R.id.txt_send)
    EditText mContent;

    @BindView(R.id.btn_submit)
    ImageView submit;

    @BindView(R.id.btn_record)
    ImageView mRecord;

    @BindView(R.id.btn_emoj)
    ImageView mEmoj;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId =bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }


    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        initToolBar();
        initAppBar();
        initEditContent();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    /**
     * 初始化toolbar退出键
     */
    protected void initToolBar()
    {
        Toolbar toolbar = mToolbar;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 由于APPBar里面具有高度变化回调
     */
    private void initAppBar(){
        mAppBarLayout.addOnOffsetChangedListener(this);
    }


    protected abstract void initEditContent();


    class MyAdapter extends RecycleAdapter<Message>{

        @Override
        protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
            switch (viewType)
            {
                case R.layout.cell_chat_text_left:
                case R.layout.cell_chat_text_right:
                    return  new TextHolder(view);
                case R.layout.cell_chat_audio_left:
                case R.layout.cell_chat_audio_right:
                    return  new AudioHolder(view);
                case R.layout.cell_chat_pic_left:
                case R.layout.cell_chat_pic_right:
                    return  new PicHolder(view);

                default:
                    return new TextHolder(view);
            }
        }

        @Override
        protected int getItemViewType(int position, Message data) {
            boolean isSelf = Objects.equals(data.getSender().getId(), Account.getUserId());
            switch (data.getType())
            {
                case Message.TYPE_STR:
                    return isSelf?R.layout.cell_chat_text_right:R.layout.cell_chat_text_left;
                case Message.TYPE_AUDIO:
                    return  isSelf?R.layout.cell_chat_audio_right:R.layout.cell_chat_audio_left;
                case Message.TYPE_PIC:
                    return isSelf?R.layout.cell_chat_pic_right:R.layout.cell_chat_pic_left;
                default:
                    return isSelf?R.layout.cell_chat_text_right:R.layout.cell_chat_text_left;

            }
        }
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
            onMoreClick();
        }
    }


    void onMoreClick(){

    }

    class BaseHolder extends RecycleAdapter.MyViewHolder<Message>{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        //允许为空，左边没有,右边有
        @Nullable
        @BindView(R.id.loading)
        Loading loading;

        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void OnBind(Message data) {
            User sender = data.getSender();
            //由于user是懒加载 所以需要重新加载
            sender.load();
            mPortraitView.setPortraitView(Glide.with(ChatFragment.this),sender);
            if(loading==null)
            {
                int status = data.getStatus();
                if(status==Message.STATUS_DONE)
                {
                    //停止Loading,并且隐藏
                    loading.stop();
                    loading.setVisibility(View.GONE);
                }else if(status==Message.STATUS_CREATED){
                    //显示可见,并且开始启动
                    loading.setVisibility(View.VISIBLE);
                    loading.setProgress(0);
                    loading.setForegroundColor(getResources().getColor(R.color.colorAccent));
                    loading.start();
                }else if(status == Message.STATUS_FAILED){
                    //停止loading，并且显示
                    loading.stop();
                    loading.setVisibility(View.VISIBLE);
                    loading.setProgress(1);
                    loading.setForegroundColor(getResources().getColor(R.color.red_900));
                }
                mPortraitView.setEnabled(status==Message.STATUS_FAILED);
            }
        }

        @OnClick(R.id.im_portrait)
        void onClickPortrait(){


            if(loading!=null)
            {

            }
        }


    }

    class TextHolder extends  BaseHolder{
        @BindView(R.id.txt_content)
        TextView mContent;


        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void OnBind(Message data) {
            super.OnBind(data);
            mContent.setText(data.getContent());

        }
    }

    class AudioHolder extends BaseHolder{

        public AudioHolder(View itemView) {
            super(itemView);
        }
    }

    class PicHolder extends BaseHolder{

        public AudioHolder(View itemView) {
            super(itemView);
        }
    }

    class EmojHolder extends  BaseHolder{

        public EmojHolder(View itemView) {
            super(itemView);
        }
    }

}
