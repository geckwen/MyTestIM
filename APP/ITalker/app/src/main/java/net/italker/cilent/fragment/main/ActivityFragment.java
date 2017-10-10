package net.italker.cilent.fragment.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.BaseFragment;
import net.common.app.PresentFragment;
import net.common.utils.DateTimeUtil;
import net.common.widget.recycle.AdapterCallBack;
import net.common.widget.recycle.EmptyView;
import net.common.widget.recycle.GalleyView;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.model.db.Session;
import net.factory.model.db.User;
import net.factory.present.session.SessionContract;
import net.factory.present.session.SessionPresent;
import net.italker.cilent.R;
import net.italker.cilent.activity.MessageActivity;
import net.italker.cilent.activity.PersonalActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 *
 */
public class ActivityFragment extends PresentFragment<SessionContract.Present>implements SessionContract.View {
    @BindView(R.id.contact)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    EmptyView emptyView;

    RecycleAdapter<Session> mAdapter;

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    protected SessionContract.Present initPresent() {
        return new SessionPresent(this);
    }



    @Override
    protected int getContentLayoutId() {
       return  R.layout.fragment_activity;
    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter = new RecycleAdapter<Session>() {
            @Override
            protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
                return new ActivityFragment.MyHolder(view);
            }

            @Override
            protected int getItemViewType(int position, Session data) {
                return R.layout.cell_chat_list;
            }
        });
        //绑定空布局
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
        mAdapter.registerClickListener(new RecycleAdapter.AdapterListenerImpl<Session>(){
            @Override
            public void onClick(RecycleAdapter.MyViewHolder holder, Session data) {
                super.onClick(holder, data);
                MessageActivity.show(getContext(),data);
            }
        });
    }

    @Override
    protected void initFirstData() {
        super.initFirstData();
        //开始初始化数据
        mPresent.start();

    }

    @Override
    public RecycleAdapter<Session> getRecycleAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataRefresh() {
        mplaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }

    class MyHolder extends RecycleAdapter.MyViewHolder<Session>{
        @BindView(R.id.im_portrait)
        PortraitView portraitView;
        @BindView(R.id.txt_name)
        TextView mNmae;
        @BindView(R.id.txt_des)
        TextView mDes;
        @BindView(R.id.txt_date)
        TextView mDate;

        public MyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void OnBind(Session data) {
            portraitView.setPortraitView(Glide.with(ActivityFragment.this),data.getPicture());
            mNmae.setText(data.getTitle());
            mDes.setText(TextUtils.isEmpty(data.getContent())?"":data.getContent());
            mDate.setText(DateTimeUtil.getSimpleDate(data.getModifyAt()));

        }


    }
}
