package net.italker.cilent.fragment.main;


import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.BaseFragment;
import net.common.app.PresentFragment;
import net.common.factory.present.BaseContract;
import net.common.widget.recycle.AdapterCallBack;
import net.common.widget.recycle.EmptyView;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.main.present.contact.ContactContract;
import net.factory.main.present.contact.ContactPresent;
import net.factory.model.card.UserCard;
import net.factory.model.db.User;
import net.italker.cilent.R;
import net.italker.cilent.activity.MessageActivity;
import net.italker.cilent.activity.PersonalActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends PresentFragment<ContactContract.Present> implements ContactContract.ContactView {

    @BindView(R.id.contact)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    EmptyView emptyView;

    RecycleAdapter<User> mAdapter;

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter = new RecycleAdapter<User>() {
            @Override
            protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
                return new MyHolder(view,this);
            }

            @Override
            protected int getItemViewType(int position, User data) {
                return R.layout.cell_contact_list;
            }
        });
        //绑定空布局
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
        mAdapter.registerClickListener(new RecycleAdapter.AdapterListenerImpl<User>(){
            @Override
            public void onClick(RecycleAdapter.MyViewHolder holder, User data) {
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
    public void showError(@StringRes int str) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void setPresent(ContactContract.Present present) {

    }

    @Override
    public RecycleAdapter<User> getRecycleAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataRefresh() {
        //进行界面操作
        mplaceHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }

    class MyHolder extends RecycleAdapter.MyViewHolder<User>{
        @BindView(R.id.im_portrait)
        PortraitView portraitView;
        @BindView(R.id.txt_name)
        TextView mNmae;
        @BindView(R.id.txt_des)
        TextView mDes;

        public MyHolder(View itemView, AdapterCallBack adapterCallBack) {
            super(itemView, adapterCallBack);
        }

        @Override
        protected void OnBind(User data) {
            portraitView.setPortraitView(Glide.with(ContactFragment.this),data);
            mNmae.setText(data.getName());
            mDes.setText(data.getDesc());
        }
        @OnClick(R.id.im_portrait)
        void onClikPortrait()
        {
            PersonalActivity.show(getContext(),mData.getId());
        }

    }

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    protected ContactContract.Present initPresent() {
        return new ContactPresent(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }
}
