package net.italker.cilent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.common.app.PresentToolBarActivity;
import net.common.app.ToolBarActivity;
import net.common.factory.present.BaseContract;
import net.common.widget.recycle.RecycleAdapter;
import net.common.widget.recycle.a.PortraitView;
import net.factory.model.view.MemberUserModel;
import net.factory.present.group.GroupCreateContract;
import net.factory.present.group.GroupMemberContract;
import net.factory.present.group.GroupMemberPresenter;
import net.italker.cilent.R;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class GroupMemberActivity extends PresentToolBarActivity<GroupMemberContract.Presenter>
        implements GroupMemberContract.View{
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    private static final String KEY_GROUP_ADMIN="KEY_GROUP_ADMIN";

    private RecycleAdapter<MemberUserModel> mAdapter;

    private String groupId;
    private boolean mIsAdmin;

    public static void show(Context context,String groupId)
    {
        show(context,groupId,false);
    }
    public static void show(Context context,String groupId,boolean isAdmin)
    {
        if(TextUtils.isEmpty(groupId))
            return;
        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra(KEY_GROUP_ID,groupId);
        intent.putExtra(KEY_GROUP_ADMIN,isAdmin);
        context.startActivity(intent);
    }

    public static void showAdmin(Context context,String groupId)
    {
        show(context,groupId,true);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_group_member;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        String groupId = bundle.getString(KEY_GROUP_ID);
        boolean mIsAdmin = bundle.getBoolean(KEY_GROUP_ADMIN)
        this.groupId = groupId;
        this.mIsAdmin = mIsAdmin;
        return !TextUtils.isEmpty(groupId);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        setTitle(R.string.title_member_list);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new RecycleAdapter<MemberUserModel>() {
            @Override
            protected MyViewHolder onCreaterViewHolde(View view, int viewType) {
                return new GroupMemberActivity.ViewHolder(view);
            }

            @Override
            protected int getItemViewType(int position, MemberUserModel data) {
                return R.layout.cell_group_create_contact;
            }

        });
    }

    @Override
    public void initData() {
        super.initData();
        //开始刷新
        mPresent.refresh();
    }

    @Override
    protected GroupMemberContract.Presenter initPresent() {
        return new GroupMemberPresenter(this);
    }


    @Override
    public RecycleAdapter<MemberUserModel> getRecycleAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataRefresh() {
        //隐藏loading
        showHide();
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    class ViewHolder extends RecycleAdapter.MyViewHolder<MemberUserModel>
    {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        @BindView(R.id.txt_name)
        TextView mNmae;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.cb_select).setVisibility(View.GONE);
        }

        @Override
        protected void OnBind(MemberUserModel model) {
            mPortrait.setPortraitView(Glide.with(GroupMemberActivity.this),model.getPortrait());
            mNmae.setText(model.getName());
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick(){
            PersonalActivity.show(GroupMemberActivity.this,mData.getUserId());
        }
    }
}
